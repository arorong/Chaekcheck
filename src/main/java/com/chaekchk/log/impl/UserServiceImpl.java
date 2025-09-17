package com.chaekchk.log.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaekchk.log.UserService;
import com.chaekchk.log.impl.dao.UserDAO;
import com.chaekchk.log.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	//회원 로그인
	@Override
	public UserVO loginSelectOne(UserVO vo){
		return userDAO.loginSelectOne(vo);
	}
	
	//회원가입
	@Override
	public int insertUser(UserVO vo) {
		return userDAO.insertUser(vo);
	}
	
	//이메일 중복 확인
	@Override
	public UserVO checkEmail(String email) {
		return userDAO.checkEmail(email);
	}

	//닉네임 중복 확인
	@Override
	public UserVO checkNick(String nick) {
		return userDAO.checkNick(nick);
	}

	//비밀번호 찾기
	@Override
	public String pwFind(UserVO vo) {
		return userDAO.pwFind(vo);
	}

	//비밀번호 변경
	@Override
	 public boolean updatePw(String email, String encodedPassword) {
        System.out.println("UserServiceImpl - updatePassword 호출: " + email);
        try {
            int result = userDAO.updatePw(email, encodedPassword);
            return result > 0;
        } catch (Exception e) {
            System.err.println("비밀번호 업데이트 중 오류: " + e.getMessage());
            return false;
        }
	}
}
