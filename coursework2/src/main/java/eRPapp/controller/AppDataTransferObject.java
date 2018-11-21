package eRPapp.controller;

import java.util.Date;

import eRPapp.domain.AccountType;
import eRPapp.domain.User;

public class AppDataTransferObject {

	private User user;
	private String email;
	private String familyName;
	private String givenName;
	private Date dateOfBirth;
	private String address;
	private String password;
	private String passwordCheck;
	private String remark;
	private AccountType accountType;
	
	public AppDataTransferObject() {
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		email = this.user.getEmail();
		familyName = this.user.getFamilyName();
		givenName = this.user.getGivenName();
		dateOfBirth = this.user.getDateOfBirth();
		address = this.user.getAddress();
		password = this.user.getPassword();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}