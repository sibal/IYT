package iyt.models;

import java.util.Date;
import iyt.enums.*;
import javax.persistence.Id;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

@Unindexed
public class Translation {
	@Parent Key<User> author;
	@NotSaved User author_data;
	@Parent Key<Article> article;
	@NotSaved Article article_data;
	@Id Long id;
    String content;
    @Indexed Date created_at;
    @Indexed Date updated_at;
    
    
    
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

	public Article getArticle_data() {
		return article_data;
	}

	public void setArticle_data(Article article_data) {
		this.article_data = article_data;
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
