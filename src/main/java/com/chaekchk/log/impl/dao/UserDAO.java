package com.chaekchk.log.impl.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaekchk.log.vo.UserVO;

@Repository
public class UserDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	//로그인
	public UserVO loginSelectOne(UserVO vo) {
	    System.out.println("=== DAO 디버깅 ===");
		System.out.println("로그인 이메일 : " + vo.getU_email());
		  
		// 이메일로 사용자 정보 조회만 수행
		UserVO user = mybatis.selectOne("UserDAO.loginSelectOne", vo.getU_email());

		System.out.println("조회 결과: " + (user != null ? "사용자 존재" : "사용자 없음"));
		return user;
		}

	//회원가입
	public int insertUser(UserVO vo) {
		
		System.out.println("회원가입DAO");
	    System.out.println("DAO에서 받은 비밀번호(이미 암호화됨): " + vo.getU_pw());
		
		return mybatis.insert("UserDAO.insertUser", vo);
	}
	
	// 닉네임 중복 검사
	public UserVO checkNick(String nick) {
		return mybatis.selectOne("UserDAO.checkNick", nick);
	}

	// 이메일 중복 검사
	public UserVO checkEmail(String email) {
		return mybatis.selectOne("UserDAO.checkEmail", email);
	}
	
	// 비밀번호 찾기
	public String pwFind(UserVO vo) {
		return mybatis.selectOne("UserDAO.pwFind", vo);
	}
	
	// 비밀번호 변경
	 public int updatePw(String email, String encodedPassword) {
	        Map<String, Object> params = new HashMap<>();
	        params.put("u_email", email);          // XML의 #{u_email}와 일치
	        params.put("u_pw", encodedPassword);   // XML의 #{u_pw}와 일치
	        return mybatis.update("UserDAO.updatePw", params);
	    }
	 
	 //회원 탈퇴
	 public int delAccount(String email) {
		 return mybatis.update("UserDAO.delAccount", email);
	 }
	

}
