package eRPapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name="BIC_RECORD")
public class BioIdCode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)		int id;
	@Column(unique=true, nullable=false)				String bioIdCode;
	@Column(name="USED", nullable=false)	int usage; // should be 0 by default, change to 1 if used.

	public BioIdCode() {
		
	}
	
	public BioIdCode(String bioIdCode, int usage) {
		this.bioIdCode = bioIdCode;
		this.usage = usage;
	}
	
	// Mapping
	@OneToOne(mappedBy="bioIdCode")	
	private User user;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getBioIdCode() {
		return bioIdCode;
	}
	public void setBioIdCode(String bioIdCode) {
		this.bioIdCode = bioIdCode;
	}
	public int getUsage() {
		return usage;
	}
	public void setUsage(int usage) {
		this.usage = usage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
