package com.chaekchk.view;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.chaekchk.log.UserService;
import com.chaekchk.log.impl.SimpleMailMessageService;
import com.chaekchk.log.vo.UserVO;

@SessionAttributes({"userNO", "userEmail", "userNick", "userPic"})
@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private SimpleMailMessageService simpleMailMessage;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	//로그인
	@RequestMapping("/loginUser.log")
	@ResponseBody
	public String loginUser(@RequestBody UserVO vo, Model model, HttpServletResponse response) {
		
		System.out.println("=== 로그인 디버깅 시작 ===");
	    System.out.println("입력받은 이메일: " + vo.getU_email());
	    System.out.println("입력받은 비밀번호: " + vo.getU_pw());
	    
		String pw = vo.getU_pw();
		UserVO user = userService.loginSelectOne(vo); //이메일로 회원 조회
		
		System.out.println("user는 " + user);
		
		if(user != null) {
			System.out.println("사용자 존재함");
	        System.out.println("DB 이메일: " + user.getU_email());
	        System.out.println("DB 비밀번호(암호화됨): " + user.getU_pw());
	        System.out.println("입력 비밀번호(평문): " + pw);
	        
			boolean isPwMatch = encoder.matches(pw, user.getU_pw());
	        
			System.out.println("비밀번호 매칭 결과: " + isPwMatch);
	        System.out.println("암호화 매칭: " + isPwMatch);
	        
			
			if(isPwMatch) { //로그인 성공
				model.addAttribute("userNO", user.getU_no());
				model.addAttribute("userEmail", user.getU_email());
				model.addAttribute("userNick", user.getU_nick());
				model.addAttribute("userPic", user.getU_pic());
				
				// Cookie 설정
				Cookie loginCookie = new Cookie("loginCookie", user.getU_email());
				loginCookie.setPath("/"); // 쿠키 저장 경로는 기본 url 경로 = 홈페이지 시작 url
				loginCookie.setMaxAge(60*60*24*90); // 저장 기간 90일
				response.addCookie(loginCookie); // response에 쿠키 담아서 클라이언트에 보냄
				
				System.out.println("쿠키 생성 완료");
				System.out.println("로그인 성공!");
				
				return "main.log";
			} else {
				System.out.println("사용자가 존재하지 않음");
			}
	    }
	    System.out.println("로그인 실패");
	    return "0";
	  }
	
	// 로그아웃
	@RequestMapping("/logout.log")
    public String logout(HttpServletRequest request, HttpServletResponse response, 
            SessionStatus status) {
		
		// 세션 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // SessionAttributes 정리
        status.setComplete();
        
        // 쿠키 삭제
        Cookie loginCookie = new Cookie("loginCookie", "");
        loginCookie.setPath("/"); // 쿠키 저장 경로는 기본 url 경로 = 홈페이지 시작 url
		loginCookie.setMaxAge(0); // 기간 삭제
		response.addCookie(loginCookie); // response에 쿠키 담아서 클라이언트에 보냄
		
		System.out.println("로그아웃 성공!");
        return "redirect:/main.log";
    }
	
	//회원가입
	@RequestMapping("/registerUser.log")
	public String insertUser(UserVO vo) {
		System.out.println("회원가입 컨트롤러");
	    System.out.println("입력받은 평문 비밀번호: " + vo.getU_pw());
	    
	    try {
	    //비밀번호 암호화
	    String encodedPassword = encoder.encode(vo.getU_pw());
	    vo.setU_pw(encodedPassword);
	    
		userService.insertUser(vo);
	    System.out.println("암호화된 비밀번호: " + encodedPassword);
		
		return "redirect:/main.jsp";
	    } catch(Exception e) {
	    	System.err.println("회원가입 오류: " + e.getMessage());
	        e.printStackTrace();
	        return "error";
	    }
	    
	}
	// 이메일 중복 체크
	@RequestMapping("/checkEmail.log")
	@ResponseBody
	public int checkEmail(UserVO vo) throws Exception{
		int count = 0;
		if (userService.checkEmail(vo.getU_email()) == null) {
			System.out.println("중복 없음");
			count = 0; // 중복 x, 사용 가능
		} else {
			System.out.println("중복 있음");
			count = 1; // 중복 o, 사용 불가
		}
		System.out.println("반환값 : " + count);
		return count;
	}

	// 닉네임 중복 체크
	@RequestMapping("/checkNick.log")
	@ResponseBody
	public int checkNick(UserVO vo) throws Exception {
		int count = 0;
		if (userService.checkNick(vo.getU_nick()) == null) {
			System.out.println("중복 없음");
			count = 0; // 중복 x, 사용 가능
		} else {
			System.out.println("중복 있음");
			count = 1; // 중복 o, 사용 불가
		}
		System.out.println("반환값 : " + count);
		return count;
	}
	
	//비밀번호 찾기 이메일 보내기
	@RequestMapping("/sendFindPwLink.log")
	@ResponseBody
	public String pwMailLink(UserVO vo) {
		System.out.println("비밀번호 링크 발송 메서드를 탔습니다. : " + vo.getU_email());
		String email = userService.pwFind(vo);
		if (email == null || email.isEmpty()) {
			return "error";
		} else {
			//이메일 발송
			try {
				simpleMailMessage.sendFinePw(email); // 메일 서비스 호출 추가
				return "success"; // email -> "success" 변경 (보안상 이메일 주소 노출 방지)
			} catch (Exception e) {
				System.out.println("이메일 전송 중 오류 발생: " + e.getMessage());
				return "error";
			}
		}
	}
	
	//비밀번호 변경
	@RequestMapping(value = "/resetPassword.log", method = RequestMethod.POST)
	@ResponseBody
	public String resetPassword(@RequestParam("u_email") String email, 
	                          @RequestParam("u_pw") String password) {
	    try {
	    	if (email == null || email.trim().isEmpty() ||
	            password == null || password.trim().isEmpty()) {
	            return "error";
	        }
	        // 비밀번호 암호화 추가
	        String encodedPassword = encoder.encode(password);
	        
	        // 비밀번호 업데이트
	        boolean success = userService.updatePw(email, encodedPassword);
	        if (success) {
	            return "success";
	        } else {
	            return "error";
	        }
	    } catch (Exception e) {
	        System.out.println("비밀번호 재설정 중 오류: " + e.getMessage());
	        return "error";
	    }
	}
	
}

