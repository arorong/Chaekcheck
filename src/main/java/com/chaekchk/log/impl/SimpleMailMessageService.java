package com.chaekchk.log.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SimpleMailMessageService {
	private final JavaMailSender mailSender;
	
	@Autowired
	public SimpleMailMessageService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
	}
	
	// 이메일 보낼 양식!
		public String sendFinePw(String email) {
			String setFrom = "chaekcheck.books@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
			String toMail = email;
			String title = "[책첵]비밀번호 재설정 안내 메일"; // 이메일 제목
			// 수정된 부분: 올바른 링크 생성 
			String resetLink = "http://localhost:8090/log/changePwPage.log?u_email=" + email;
			String content = "안녕하세요. 책첵입니다." +  
					"<br><br>" + "아래 비밀번호 변경 버튼을 눌러 새로운 비밀번호를 설정해주세요." +
					"<br><br>" + 
					"<a href='" + resetLink + "' style='background-color: #46A8F2; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>비밀번호 변경하기</a>" +
					"<br><br>"; // 이메일 내용 삽입 
					
			mailSend(setFrom, toMail, title, content); 
			return "success"; // 성공 시 반환값 추가
		}
		
		// 이메일 전송 메소드
		public void mailSend(String setFrom, String toMail, String title, String content) {
			try {
				MimeMessage message = mailSender.createMimeMessage();
				// true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
				// MimeMessageHelper 생성
				MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
				
				helper.setFrom(setFrom);
				helper.setTo(toMail);
				helper.setSubject(title);
				// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
				helper.setText(content, true);
				
				mailSender.send(message);
				System.out.println("이메일 발송 : " + content);
			} catch (MessagingException e) {
				System.out.println("메일 전송 실패");
				e.printStackTrace();
				throw new RuntimeException("메일 전송에 실패했습니다."); // 예외를 상위로 전달
			}
		}
}