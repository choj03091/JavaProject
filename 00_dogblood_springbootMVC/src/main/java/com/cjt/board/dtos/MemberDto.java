package com.cjt.board.dtos;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias(value = "memberDto")
public class MemberDto {
	
	private int memberId;
	private String id;
	private String name;
	private String password;
	private String email;
	private String address;
	private String role;
	private Date regDate;
	private String delFlag;
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	@Override
	public String toString() {
		return "MemberDto [memberId=" + memberId + ", id=" + id + ", name=" + name + ", password=" + password
				+ ", email=" + email + ", address=" + address + ", role=" + role + ", regDate=" + regDate + ", delFlag="
				+ delFlag + "]";
	}
	
	
}




