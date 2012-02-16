package iyt.models;

import java.util.Date;
import iyt.enums.*;
import javax.persistence.Id;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

public class Article {
	@Indexed @Id Long id;
    @Parent Key<User> author;
	@NotSaved User author_data;
	String content;
	@Indexed String sid;
    Vender vender;
    Category category;
    Date created_at;
    Date updated_at;
    
    public Key<Article> getKey() {
    	return new Key<Article>(Article.class, id);
    }
    
    
    public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public Article() {
    	created_at = new Date();
    }
    
    public Article(User author) {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Vender getVender() {
		return vender;
	}

	public void setVender(Vender vender) {
		this.vender = vender;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
