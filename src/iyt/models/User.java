package iyt.models;



import iyt.enums.AppRole;
import iyt.enums.Interest;
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
	private String username;   // 이메일 (아이디)
    private String nick;       // 닉네임
    private String name;       // 네임
    private String fid;        // 페이스북 아이디(키값)
    private String profile_image_url;    // 프로필 이미지의 url

	private String password;   //비번
    @NotSaved
    private String password_c;  // 일시 정보
    @NotSaved
    private int step;           // 일시적인 정보
    
    private String face_access;    //페북 엑세스토큰
    private String twit_authT="";  //트위터 authToken
    private String twit_authTS=""; //트위터 authTokenSecret
    
    
    //private int rank; How can we count the rank?
    private int numFans;    // 팬수
    private int voting;     // voting 합
    
   	
   	// 임시적으로 저장하는 정보들.
   	@NotSaved String firstLanguage;   // 첫번째 언어
   	@NotSaved String secondLanguage;  // 두번째 언어
   	@NotSaved int numFirstLan;        // 첫번째 언어로 번역한 수
   	@NotSaved int numSecondLan;       // 두번째 언어로 번역한 수
   	@NotSaved int numOtherLan;        // 그밖에 다른 언어로 번역한 수
   	@NotSaved String interests_str;   // 흥미 정보 전달을 위해
   	@NotSaved String language1;       // 가입시 언어 선택, 어래로 쭉.
   	@NotSaved String language2;
   	@NotSaved String language3;
   	@NotSaved String language4;
   	@NotSaved String language5;
   	@NotSaved String firstTran;       // 가장 많이 번역한 종류
   	@NotSaved String secondTran;      // 둘째로 많이 번역한 종류
   	@NotSaved int firstTranNum;       // 위의 각 숫자, 아래도.
   	@NotSaved int secondTranNum;
   	@NotSaved String opassword;       // 비번 확인을 위한 임시 정보
   	@NotSaved int isMyFriend; // For being a fan
   	
   	// 번역 정보를 저장하기 위한 list
   	@Embedded List<TransInformation> transinfo= new ArrayList<TransInformation>();
   	
   	// 본인이 만든 request를 저장
   	List<Key<TransRequest>> requestInfo= new ArrayList<Key<TransRequest>>(); // For translation Request
   	
   	// 관심 정보 저장
   	List<Interest> interests = new ArrayList<Interest>();
   	
   	// 언어 정보 저장
   	List<Language> languages = new ArrayList<Language>();
   	   	
   	
   	
   	
   	
	public String getOpassword() {
		return opassword;
	}

	public void setOpassword(String opassword) {
		this.opassword = opassword;
	}

	public String getFirstTran() {
		return firstTran;
	}

	public void setFirstTran(String firstTran) {
		this.firstTran = firstTran;
	}

	public String getSecondTran() {
		return secondTran;
	}

	public void setSecondTran(String secondTran) {
		this.secondTran = secondTran;
	}

	public int getFirstTranNum() {
		return firstTranNum;
	}

	public void setFirstTranNum(int firstTranNum) {
		this.firstTranNum = firstTranNum;
	}

	public int getSecondTranNum() {
		return secondTranNum;
	}

	public void setSecondTranNum(int secondTranNum) {
		this.secondTranNum = secondTranNum;
	}

	public List<TransInformation> getTransinfo() {
		return transinfo;
	}

	public void setTransinfo(List<TransInformation> transinfo) {
		this.transinfo = transinfo;
	}
	
	public List<Key<TransRequest>> getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(List<Key<TransRequest>> requestInfo) {
		this.requestInfo = requestInfo;
	}
	
	
	// For profile ///////////
	
		
	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public List<Interest> getInterests() {
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public int getVoting() {
		return voting;
	}

	public String getInterests_str() {
		return interests_str;
	}

	public void setInterests_str(String interests_str) {
		this.interests_str = interests_str;
	}

	public void setVoting(int voting) {
		this.voting = voting;
	}

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



	public String getLanguage1() {
		return language1;
	}

	public void setLanguage1(String language1) {
		this.language1 = language1;
	}

	public String getLanguage2() {
		return language2;
	}

	public void setLanguage2(String language2) {
		this.language2 = language2;
	}

	public String getLanguage3() {
		return language3;
	}

	public void setLanguage3(String language3) {
		this.language3 = language3;
	}

	public String getLanguage4() {
		return language4;
	}

	public void setLanguage4(String language4) {
		this.language4 = language4;
	}

	public String getLanguage5() {
		return language5;
	}

	public void setLanguage5(String language5) {
		this.language5 = language5;
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



	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

    
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
