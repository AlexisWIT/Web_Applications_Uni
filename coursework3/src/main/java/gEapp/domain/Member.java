package gEapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Member {
	
	@Id
	@Column(name="key")
	int id;
	
	String name;
	int mumKey;
	int dadKey;
	int birthday;
	String gender;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("m")
	public int getMumKey() {
		return mumKey;
	}
	
	public void setMumKey(int mumKey) {
		this.mumKey = mumKey;
	}
	
	@JsonProperty("f")
	public int getDadKey() {
		return dadKey;
	}
	
	public void setDadKey(int dadKey) {
		this.dadKey = dadKey;
	}
	
	@JsonProperty("dob")
	public int getBirthday() {
		return birthday;
	}
	
	public void setBirthday(int birthday) {
		this.birthday = birthday;
	}
	
	@JsonProperty("g")
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	

}
