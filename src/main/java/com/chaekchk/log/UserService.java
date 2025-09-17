package com.chaekchk.log;

import com.chaekchk.log.vo.UserVO;

public interface UserService {
	
	UserVO loginSelectOne(UserVO vo); // 한 명의 회원 정보 담기
	int insertUser(UserVO vo); // 회원가입
	UserVO checkEmail(String email); // 이메일 중복 확인
	UserVO checkNick(String nick); // 닉네임 중복 확인
	String pwFind(UserVO vo); //비밀번호 찾기
	boolean updatePw(String email, String password); //비밀번호 변경
	
	
}
