package iyt.models;

import iyt.enums.Language;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import javax.persistence.Id;
import iyt.enums.*;

public class Mayors {
		
		@Indexed @Id Long id;
		String userid;
		Key<User> mayor;
		Vender vender;
		int sumofvotes;
		
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
	
