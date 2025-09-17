package com.chaekchk.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chaekchk.log.UserService;
import com.chaekchk.log.vo.UserVO;

@Controller
public class ChaekcheckController {
	@Autowired
	private UserService userService;

	
	//메인으로
	@RequestMapping("/main.log") 
	public String mainPage() {
		return "/main.jsp";
	}
	
	//비밀번호 찾기 페이지로 이동
	@RequestMapping("/findPw.log")
	public String findPwPage() {
		return "WEB-INF/login/pwFindE.jsp";
	}

	//이메일 링크 클릭 시 비밀번호 변경 페이지로 이동()
	@RequestMapping("/changePwPage.log")
	public String resetPasswordForm(@RequestParam("u_email") String email, Model model) {
	    try {
	        // 이메일이 실제 존재하는지 확인
	        UserVO userVO = new UserVO();
	        userVO.setU_email(email);
	        String existingEmail = userService.pwFind(userVO);
	        if (existingEmail == null || existingEmail.isEmpty()) {
	            model.addAttribute("error", "유효하지 않은 링크입니다.");
	            return "error";
	        }
	        
	        model.addAttribute("u_email", email);
	        return "WEB-INF/login/pwChange.jsp"; // JSP 페이지명
	        
	    } catch (NumberFormatException e) {
	        model.addAttribute("error", "유효하지 않은 링크입니다.");
	        return "error";
	    } catch (Exception e) {
	        model.addAttribute("error", "오류가 발생했습니다.");
	        return "error";
	    }
	}

	//마이 페이지로 이동
	@RequestMapping("/myPageHome.log")
	public String myPage() {
		return "WEB-INF/user/myPageHome.jsp";
	}
	
	//프로필 수정으로 이동
	@RequestMapping("/updateProfile.log")
	public String updsteProfile() {
		return "WEB-INF/user/updateProfile.jsp";
	}
	
	
	
	
	
	
	// 회원 마이페이지
//	@RequestMapping("/myPage.log")
//	public String viewMypage(HttpSession session, Model model) {
//		String selId = (String) session.getAttribute("userID");
//		model.addAttribute("users", userService.selectOne(selId));
//		return "/WEB-INF/login/userMyInfo.jsp";
//	}
	
	
}
