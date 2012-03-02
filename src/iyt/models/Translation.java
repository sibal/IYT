package iyt.models;

import java.util.Date;
import iyt.enums.*;
import javax.persistence.Id;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.*;

/**
 *
 *	번역 정보를 저장하는 클래스입니다.
 * 
 * @author hellcodes
 *
 */

public class Translation {
	@Indexed Key<User> author;     // 번역한 사람
	@NotSaved User author_data;
	//@Parent Key<Article> article;
	//@NotSaved Article article_data;
	@Indexed @Id Long id;  
	@Indexed String sid;         // 번역한 원글의 ID (트위터, 페북에서의 고유 아이디)
    String t_content;            // 번역한 내용
    Language t_lan;              // 무엇으로 번역했는가
	String ori_content;          // 원래 내용
	Language ori_lan;            // 원래 내용의 언어
	String userid;     // the userid of author of original text
	String username; // the username of original text
	String profile_image_url;    // 원래 글을 작성한 사람의 프로필 이미지 URL
	int vender;                  // 트위터 OR 페북
    int voting;                  // 이 번역의 voting 값
    @Indexed Date created_at;
    @Indexed Date updated_at;
           
    
    public String getProfile_image_url() {
		return profile_image_url;
	}



	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getUserid() {
		return userid;
	}



	public int getVender() {
		return vender;
	}



	public void setVender(int vender) {
		this.vender = vender;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}



	public Language getT_lan() {
		return t_lan;
	}



	public void setT_lan(Language t_lan) {
		this.t_lan = t_lan;
	}



	public Language getOri_lan() {
		return ori_lan;
	}



	public void setOri_lan(Language ori_lan) {
		this.ori_lan = ori_lan;
	}



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
