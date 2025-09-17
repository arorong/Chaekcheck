package com.chaekchk.log.vo;

public class UserVO {
	private int u_no;
	private String u_email;
	private String u_nick;
	private String u_pw;
	private String u_pic;
	private String u_status;

	public int getU_no() {
		return u_no;
	}
	public void setU_no(int u_no) {
		this.u_no = u_no;
	}
	public String getU_email() {
		return u_email;
	}
	public void setU_email(String u_email) {
		this.u_email = u_email;
	}
	public String getU_nick() {
		return u_nick;
	}
	public void setU_nick(String u_nick) {
		this.u_nick = u_nick;
	}
	public String getU_pw() {
		return u_pw;
	}
	public void setU_pw(String u_pw) {
		this.u_pw = u_pw;
	}
	public String getU_pic() {
		return u_pic;
	}
	public void setU_pic(String u_pic) {
		this.u_pic = u_pic;
	}
	public String getU_status() {
		return u_status;
	}
	public void setU_status(String u_status) {
		this.u_status = u_status;
	}
	
	@Override
	public String toString() {
		return "UserVO [u_no=" + u_no + ", u_email=" + u_email + ", u_nick=" + u_nick + ", u_pw=" + u_pw + ", u_pic="
				+ u_pic + ", u_status=" + u_status + "]";
	}
	
	

}
