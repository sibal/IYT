package iyt.models;

import iyt.enums.Category;
import iyt.enums.Vender;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Parent;

/**
 * Fan 관계를 나타내기 위한 클래스입니다.
 * follower는 fan 되고,
 * followee는 target이 됩니다.
 * 
 * @author hellcodes
 *
 */
public class Followship {

	@Indexed @Id Long id;
	Key<User> follower;
	Key<User> followee;
	Date created_at;
    Date updated_at;
    
    public Followship (User follower, User followee)
    {
    	this.follower = follower.getKey();
    	this.followee = followee.getKey();
    	created_at = new Date();
    }
    
    public Followship ()
    {
    	this.follower = null;
    	this.followee = null;
    	created_at = new Date();
    }
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Key<User> getFollower() {
		return follower;
	}
	public void setFollower(Key<User> follower) {
		this.follower = follower;
	}

	public Key<User> getFollowee() {
		return followee;
	}
	public void setFollowee(Key<User> followee) {
		this.followee = followee;
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
