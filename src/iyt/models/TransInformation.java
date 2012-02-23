package iyt.models;

import iyt.enums.Language;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import javax.persistence.Id;


public class TransInformation {
		
		@Indexed @Id long id;
		Language source;
		Language dest;
		int num;
		
	public TransInformation (Language source, Language dest, int num)
	{
		this.source = source;
		this.dest = dest;
		this.num = num;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TransInformation ()
	{
		this.source = null;
		this.dest = null;
		this.num = 0;
	}
	
    public Key<TransInformation> getKey() {
    	return new Key<TransInformation>(TransInformation.class, id);
    }
	
	public Language getSource() {
		return source;
	}
	public void setSource(Language source) {
		this.source = source;
	}
	public Language getDest() {
		return dest;
	}
	public void setDest(Language dest) {
		this.dest = dest;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
		
}
	
