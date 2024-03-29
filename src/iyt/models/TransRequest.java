package iyt.models;

import java.util.Date;

import iyt.enums.Language;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import javax.persistence.Id;

import iyt.enums.*;

/**
 * 이 클래스는 번역 요청에 대한 클래스입니다.
 * 
 * @author hellcodes
 *
 */

public class TransRequest {
		
		@Indexed @Id Long id;
		Language language;             // 원 글의 언어
		String uid; // 원글 저작자의 고유값(트위터, 페이스북)
		String username; // author's name
		String profile_image_url;   // 원글 저작자의 이미지 url
		String aid; // article's id  원글의 아이디(트위터, 페이스북)
		String text;    // 원글의 내용
		Key<User> requester;   // 요청자
		@Indexed Date created_at;
	    int vender;    // 페북인가 트위터인가
			
    public void setId(Long id) {
			this.id = id;
		}

	public Key<TransRequest> getKey() {
    	return new Key<TransRequest>(TransRequest.class, id);
    }
    
    public TransRequest() {}
    
    public TransRequest(Language language, String aid, String text, Key<User> requester, int vender)
    {
    	this.language = language;
    	this.aid = aid;
    	this.text = text;
    	this.requester = requester;
    	this.vender = vender;
    	created_at = new Date();
    }

    
    
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Key<User> getRequester() {
		return requester;
	}

	public void setRequester(Key<User> requester) {
		this.requester = requester;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public int getVender() {
		return vender;
	}

	public void setVender(int vender) {
		this.vender = vender;
	}
    
    
    
		
}
	
