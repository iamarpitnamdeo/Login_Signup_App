package com.example.SBTRegistratoinApp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserDtls {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String fullname;
	private String gender;
	private String dob;
	private String address;
	private String email;
	private String password;
	private String role;
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserDtls [fullname=" + fullname + ", gender=" + gender + ", dob=" + dob + ", address=" + address
				+ ", email=" + email + ", password=" + password + ", role=" + role + "]";
	}
	
	
}
