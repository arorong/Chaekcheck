package com.chaekchk.view;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.chaekchk.log.UserService;

@SessionAttributes({"userNO", "userEmail", "userNick", "userPic"})
@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	//회원 탈퇴
	@RequestMapping("/deleteMember.log")
	public String withdrawal(HttpSession session) throws Exception {
		System.out.println("회원 탈퇴 컨트롤러");
		String email = (String)session.getAttribute("userEmail");
		
		if(email == null) {
			return "redirect:/main.log";
		}
		
		int delAcc = userService.delAcc(email);
		
		if(delAcc == 0) {
			session.invalidate(); //탈퇴 성공
			return "redirect:/main.log";
		} else {
			session.setAttribute("msg", "회원 탈퇴 실패");
			return "redirect:/myPageHome.log";
		}
	}

}
