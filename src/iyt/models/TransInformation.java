package iyt.models;

import iyt.enums.Language;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import javax.persistence.Id;

/**
 * 
 * 이 클래스는 사용자가 어디서 어디로 바뀌는 해석글을 얼마나 썼는지
 * 저장하기 위해 만든 클래스입니다. 이 클래스의 객체는
 * 각 사용자의 member list의 엘레멘트로 들어가게 됩니다.
 * 
 * 예를 들어, a라는 사용자가 영어에서 한국어로 가는 해석글을 방금 작성한다면,
 * a라는 사용자의 transinfo 라는 member에,
 * 이 클래스의 객체를 하나 생성해서 그 정보를 저장하게 됩니다. 
 * 
 * @author hellcodes
 *
 */
public class TransInformation {
		
		@Indexed @Id Long id;
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
	
