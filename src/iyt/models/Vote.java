package iyt.models;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;

public class Vote {


	@Indexed @Id Long id;
	Key<User> voter;
	Key<Translation> target;
	Date created_at;
    Date updated_at;
    
    
    public Vote(User voter, Translation target)
    {
    	this.voter = voter.getKey();
    	this.target = target.getKey();
    }
    
    public Vote()
    {
    	this.voter = null;
    	this.target = null;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Key<User> getVoter() {
		return voter;
	}
	public void setVoter(Key<User> voter) {
		this.voter = voter;
	}
	public Key<Translation> getTarget() {
		return target;
	}
	public void setTarget(Key<Translation> target) {
		this.target = target;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
    
    
	
	
}
