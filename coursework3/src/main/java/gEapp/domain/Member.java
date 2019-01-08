package gEapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Member {
	
	@Id
	@Column(name="id")
	Integer key;
	
	String name;
	Integer mumKey;
	Integer dadKey;
	Integer birthday;
	String gender;
	Integer spouseId=0;
	
	public Member() {
		super();
	}

	public Member(Integer key, String name, Integer birthday, String gender,Integer mumKey, Integer dadKey) {
		super();
		this.key = key;
		this.name = name;
		this.mumKey = mumKey;
		this.dadKey = dadKey;
		this.birthday = birthday;
		this.gender = gender;
	}
	
	public Member(Integer key, String name) {
		super();
		this.key = key;
		this.name = name;
	}

	public Integer getKey() {
		return key;
	}
	
	public void setKey(Integer key) {
		this.key = key;
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
	public Integer getDadKey() {
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

	public Integer getSpouseId() {
		return spouseId;
	}

	public void setSpouseId(Integer spouseId) {
		this.spouseId = spouseId;
	}

	@Override
	public String toString() {
		return "Member [key=" + key + ", name=" + name + ", mumKey=" + mumKey + ", dadKey=" + dadKey + ", birthday="
				+ birthday + ", gender=" + gender + ", spouseId=" + spouseId + "]";
	}

}
