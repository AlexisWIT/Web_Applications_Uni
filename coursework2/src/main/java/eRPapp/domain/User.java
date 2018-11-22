package eRPapp.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Component
@Entity(name="USERS")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)					
	private int id = -1;
	
	@Column(name="Email", unique=false , nullable=false)			String email;
	@Column(name="Family_Name", unique=false, nullable=false)		String familyName;
	@Column(name="Given_Name", unique=false, nullable=false)		String givenName;
	@Column(name="Date_of_Birth", unique=false, nullable=false)		Date dateOfBirth;
	@Column(name="Address", unique=false, nullable=false)			String address;
	@Column(name="Password", unique=false, nullable=false)			String password;
	@Transient														String passwordCheck;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="bioIdCode", referencedColumnName="id")		
	private BioIdCode bioIdCode;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="accountType", referencedColumnName="id")
	private AccountType accountType;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="vote", referencedColumnName="id")
	private Vote vote;
	
	@Transient
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	// To format dates to strings: 
	// String dateString = format.format(new Date());
	@Transient
	private String userRemark;
	@Transient
	String dateOfBirthString; // Date of birth input from Signup.jsp will be saved here.
	@Transient
	String bioIdCodeString; // bioIdCode input from Signup.jsp will be saved here.
	
	public User() {
		
	}
	
	public User(String email, String familyName, String givenName, String dateOfBirth, String address, String password, AccountType accountType) throws ParseException {
		this.email = email;
		this.familyName = familyName;
		this.givenName = givenName;
		this.dateOfBirth = toDate(dateOfBirth);
		this.address = address;
		this.password = password;
		this.accountType = accountType;
	}
	
	public Date toDate(String dateString) throws ParseException {
		Date result = format.parse(dateString);
		return result;		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDateOfBirthString() {
		return dateOfBirthString;
	}

	public void setDateOfBirthString(String dateOfBirthString) {
		this.dateOfBirthString = dateOfBirthString;
	}
	
	public String getDateOfBirthForHome() {
		String dateOfBirthForHome = format.format(dateOfBirth);
		return dateOfBirthForHome;
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

	public BioIdCode getBioIdCode() {
		return bioIdCode;
	}

	public void setBioIdCode(BioIdCode bioidCode) {
		this.bioIdCode = bioidCode;
		this.bioIdCode.setUsage(1);// mark this BIC code as used
	}

	public String getBioIdCodeString() {
		return bioIdCodeString;
	}

	public void setBioIdCodeString(String bioIdCodeString) {
		this.bioIdCodeString = bioIdCodeString;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}
	
	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	@Override
	public String toString() {
		return "UserDetail [Email=" + email + ", Password=" + password + ", FamilyName=" + familyName + ", GivenName=" + 
							givenName + ", DateOfBirth=" + dateOfBirthString + ", Address=" + address + ", BIC=" + bioIdCodeString + 
							", AccountType=" + accountType.toString() + ", Vote=" + vote +"]";
	}

}
