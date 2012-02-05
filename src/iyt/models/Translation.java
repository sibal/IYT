package iyt.models;

import java.util.Date;
import iyt.enums.*;
import javax.persistence.Id;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

public class Translation {
	@Parent Key<User> author;
	@NotSaved User author_data;
	//@Parent Key<Article> article;
	//@NotSaved Article article_data;
	@Indexed @Id Long id;
	@Indexed String sid;
    String t_content;
    String ori_content;
    int voting;
    @Indexed Date created_at;
    @Indexed Date updated_at;
    
    
    public Key<Translation> getKey() {
    	return new Key<Translation>(Translation.class, id);
    }
    
    
        
    public int getVoting() {
		return voting;
	}

	public void setVoting(int voting) {
		this.voting = voting;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getOri_content() {
		return ori_content;
	}

	public void setOri_content(String ori_content) {
		this.ori_content = ori_content;
	}

	public Translation() {
    	created_at = new Date();
    }
    
    public Translation(User author) {
    	created_at = new Date();
    	this.author = author.getKey();
    }
    
    public User getAuthor_data() {
		return author_data;
	}

	public void setAuthor_data(User author_data) {
		this.author_data = author_data;
	}


	public Key<User> getAuthor() {
		return author;
	}

	public void setAuthor(Key<User> author) {
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getT_content() {
		return t_content;
	}

	public void setT_content(String t_content) {
		this.t_content = t_content;
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
