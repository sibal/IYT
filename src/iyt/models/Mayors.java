package iyt.models;

import iyt.enums.Language;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import javax.persistence.Id;
import iyt.enums.*;

/**
 * 
 * Mayors 클래스는 mayor와 각 계정(트위터, 페이스북)의 1-1 매핑에 대한 클래스가 아니라,
 * Mayor를 구할 수 있는 기본 정보를 제공하는 클래스 입니다. 예를 들어, a라는 사람이 b라는 계정에 대해 해석글을 올리면
 * a가 b의 mayor가 아니래도 Mayors 객체가 생성되어 a가 b의 글들에 기여하는 정보(voting 수)를 갖게 됩니다.
 * 즉, Mayors 클래스는 Mayor를 찾기 위한 기본 정보를 저장하는 클래스입니다. 
 * 
 * @author hellcodes
 *
 */

public class Mayors {
		
		@Indexed @Id Long id;
		String userid;       // 계정 아이디(트위터, 페이스북)
		Key<User> mayor;     // 번역자
		Vender vender;       // 트위터 인가 페이스북인가
		int sumofvotes;      // 이 번역자가 이 계정에 대한 voting 값의 총합
		
	public Mayors (String userid, Vender vender)
	{
		this.userid = userid;
		this.vender = vender;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Mayors ()
	{
		this.sumofvotes = 0;
	}
	
    public Key<Mayors> getKey() {
    	return new Key<Mayors>(Mayors.class, id);
    }

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Key<User> getMayor() {
		return mayor;
	}

	public void setMayor(Key<User> mayor) {
		this.mayor = mayor;
	}

	public Vender getVender() {
		return vender;
	}

	public void setVender(Vender vender) {
		this.vender = vender;
	}

	public int getSumofvotes() {
		return sumofvotes;
	}

	public void setSumofvotes(int sumofvotes) {
		this.sumofvotes = sumofvotes;
	}
		
}
	
