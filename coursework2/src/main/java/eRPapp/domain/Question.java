package eRPapp.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="REFERENDUM")
public class Question {
	
	@Id																private int refId;
	@Column(name="REFERENDUM_TITLE", unique=true, nullable=false)	String title;
	@Column(name="OPEN", unique=false, nullable=false)				int status; // should be 1 if open for vote, vice versa.
	
	public Question() {
		
	}
	
	public Question(int refId, String title, int status) {
		super();
		this.refId = refId;
		this.title = title;
		this.status = status;
	}
	
	// Mapping
	@OneToMany(mappedBy="question")
	private Set<Option> option;
	
	public int getRefId() {
		return refId;
	}
	public void setRefId(int refId) {
		this.refId = refId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Option> getOption() {
		return option;
	}

	public void setOption(Set<Option> option) {
		this.option = option;
	}

}
