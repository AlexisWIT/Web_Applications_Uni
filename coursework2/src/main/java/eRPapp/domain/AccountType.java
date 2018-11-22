package eRPapp.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="ACCOUNT_TYPES")
public class AccountType {
	
	@Id						private int id;
	@Column(nullable=false)	private String accountType;
	
	public AccountType() {
		
	}
	
	public AccountType (int id, String accountType) {
		this.id = id;
		this.accountType = accountType;
	}
	
	// Mapping
	@OneToMany(mappedBy="accountType")
	private Set<User> users;
	
	
	public int getId() {
		return id;
	}

	public void setId(int typeId) {
		this.id = typeId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public String toString() {
		return accountType;
	}

}
