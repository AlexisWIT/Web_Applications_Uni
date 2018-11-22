package eRPapp.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="REFERENDUM_OPTIONS")
public class Option {
	
	@Id											private int id;

	@ManyToOne
	@JoinColumn(name="REF_ID")					private Question question;
	@Column(name="OPTIONS", nullable=false)		String option;
	@Column(name="VOTE_COUNT",nullable=false)	int count;
	
	public Option() {

	}

	public Option(int optId, Question question, String option, int count) {
		super();
		this.id = optId;
		this.question = question;
		this.option = option;
		this.count = count;
	}
	
	// Mapping
	@OneToMany(mappedBy="option")
	private Set<Vote> vote;
	
	public int getId() {
		return id;
	}
	public void setId(int optId) {
		this.id = optId;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int inCount) {
		this.count = count+inCount;
	}

	public Set<Vote> getVote() {
		return vote;
	}

	public void setVote(Set<Vote> vote) {
		this.vote = vote;
	}

}
