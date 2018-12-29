package gEapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Member {
	
	@Id
	@Column(name="key")
	Integer id;
	
	String name;
	Integer mumKey;
	Integer dadKey;
	Integer birthday;
	String gender;
	
	
	
	public Member(Integer id, String name, Integer birthday, String gender,Integer mumKey, Integer dadKey) {
		super();
		this.id = id;
		this.name = name;
		this.mumKey = mumKey;
		this.dadKey = dadKey;
		this.birthday = birthday;
		this.gender = gender;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("m")
	public Integer getMumKey() {
		return mumKey;
	}
	
	public void setMumKey(Integer mumKey) {
		this.mumKey = mumKey;
	}
	
	@JsonProperty("f")
	public int getDadKey() {
		return dadKey;
	}
	
	public void setDadKey(Integer dadKey) {
		this.dadKey = dadKey;
	}
	
	@JsonProperty("dob")
	public Integer getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Integer birthday) {
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
