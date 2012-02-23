package iyt.models;



import iyt.enums.AppRole;
import iyt.enums.Language;
import java.util.List;
import java.util.ArrayList;

import java.util.EnumSet;
import java.util.Set;
import javax.persistence.Id;
import org.springframework.security.core.userdetails.UserDetails;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Parent;
import javax.persistence.Embedded;

/**
 * Custom user object for the application.
 *
 * @author Inkyu Lee
 */

public class User implements UserDetails {
	private static final long serialVersionUID = 2239467071084743509L;
	
	@Id 
	private String username;
    private String nick;
    private String name;
    private String fid;
    private String profile_image_url; 

    //private int rank; How can we count the rank?
    private int numFans;
    
    @Parent Key<User> mayor;
   	@NotSaved User mayor_data;
    
   	@NotSaved int isMyFriend; // For being a fan
   	
   	// for displaying profile
   	@NotSaved String firstLanguage;
   	@NotSaved String secondLanguage;
   	@NotSaved int numFirstLan;
   	@NotSaved int numSecondLan;
   	@NotSaved int numOtherLan;
   	
   	//JR's codes
   	@Embedded List<TransInformation> transinfo= new ArrayList<TransInformation>();
   	   	
	public List<TransInformation> getTransinfo() {
		return transinfo;
	}

	public void setTransinfo(List<TransInformation> transinfo) {
		this.transinfo = transinfo;
	}
	
	// For profile ///////////
	
	
	
	public String getFirstLanguage() {
		return firstLanguage;
	}

	public void setFirstLanguage(String firstLanguage) {
		this.firstLanguage = firstLanguage;
	}

	public String getSecondLanguage() {
		return secondLanguage;
	}

	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public int getNumFirstLan() {
		return numFirstLan;
	}

	public void setNumFirstLan(int numFirstLan) {
		this.numFirstLan = numFirstLan;
	}

	public int getNumSecondLan() {
		return numSecondLan;
	}

	public void setNumSecondLan(int numSecondLan) {
		this.numSecondLan = numSecondLan;
	}
	
	public int getNumOtherLan() {
		return numOtherLan;
	}

	public void setNumOtherLan(int numOtherLan) {
		this.numOtherLan = numOtherLan;
	}
	
	//////////////////////////////

	public int getIsMyFriend() {
		return isMyFriend;
	}

	public void setIsMyFriend(int isMyFriend) {
		this.isMyFriend = isMyFriend;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public int getNumFans() {
		return numFans;
	}

	public void setNumFans(int numFans) {
		this.numFans = numFans;
	}

	public Key<User> getMayor() {
		return mayor;
	}

	public void setMayor(Key<User> mayor) {
		this.mayor = mayor;
	}

	public User getMayor_data() {
		return mayor_data;
	}

	public void setMayor_data(User mayor_data) {
		this.mayor_data = mayor_data;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	private String password;
    @NotSaved
    private String password_c;
    @NotSaved
    private int step;
    
    private String face_access;
    private String twit_authT="";
    private String twit_authTS="";
    
    public String getTwit_authT() {
		return twit_authT;
	}

	public void setTwit_authT(String twit_authT) {
		this.twit_authT = twit_authT;
	}

	public String getTwit_authTS() {
		return twit_authTS;
	}

	public void setTwit_authTS(String twit_authTS) {
		this.twit_authTS = twit_authTS;
	}

	public String getFace_access() {
		return face_access;
	}

	public void setFace_access(String face_access) {
		this.face_access = face_access;
	}

	private Set<AppRole> authorities;

    public Key<User> getKey() {
    	return new Key<User>(User.class, username);
    }
    
    public User() {
    	java.lang.System.out.println("GaeUser(0)");
    }
    
    public User(String userid, String password) {
    	java.lang.System.out.println("GaeUser(2)");
        this.username = userid;
        this.nick = "ddd";
        this.name = "kkk";
        this.password = password;
        this.password_c = null;
        this.authorities = EnumSet.of(AppRole.USER);        
    }

    public User(String userid, String password, String password_c, String nick, String name, Set<AppRole> authorities, boolean enabled) {
    	java.lang.System.out.println("GaeUser(5)");
    	this.username = userid;
        this.nick = nick;
        this.name = name;
        this.password = password;
        this.password_c = password_c;
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "GaeUser{" +
                "userId='" + username + '\'' +
                ", nick='" + nick + '\'' +
                ", name='" + name + '\'' +
                ", authorities=" + authorities +
                '}';
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String userid) {
		this.username = userid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword_c() {
		return password_c;
	}

	public void setPassword_c(String password_c) {
		this.password_c = password_c;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Set<AppRole> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<AppRole> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
