package eRPapp.domain;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name="VOTES")
public class Vote {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)	int id;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(referencedColumnName="id")
	private Option option;

	
	public Vote() {

	}

	public Vote(int id, Option option) {
		super();
		this.id = id;
		this.option = option;
	}
	
	// Mapping
	@OneToOne(mappedBy="vote")
	private User user;
	
	public Vote(User user, Option option) {
		this.user = user;
		this.option = option;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public int getId() {
		return id;
	}

	public void setId(int voteId) {
		this.id = voteId;
	}

	public User getUsers() {
		return user;
	}

	public void setUsers(User user) {
		this.user = user;
	}
	
	

}
