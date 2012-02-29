package iyt.enums;

import java.util.List;
import java.util.ArrayList;

/*
 * 
 * 			<!-- 관심사 선택 부분 왼쪽 다단 --> 
			<div id="centerMainLeft"> 
				<p><input type="checkbox" name="checkForm" id="interest1" /> Art & Design</p> 
				<p><input type="checkbox" name="checkForm" id="interest3" /> Business</p> 
				<p><input type="checkbox" name="checkForm" id="interest5" /> Deals</p> 
				<p><input type="checkbox" name="checkForm" id="interest7" /> Family</p> 
				<p><input type="checkbox" name="checkForm" id="interest9" /> Food & Drink</p> 
				<p><input type="checkbox" name="checkForm" id="interest11" /> Health</p> 
				<p><input type="checkbox" name="checkForm" id="interest13" /> News</p> 
				<p><input type="checkbox" name="checkForm" id="interest15" /> Sci &amp; Tech</p> 
				<p><input type="checkbox" name="checkForm" id="interest17" /> Travel</p> 
            </div> 
            
			<!-- 관심사 선택 부분 오른쪽 다단 --> 
			<div id="centerMainRight"> 
				<p><input type="checkbox" name="checkForm" id="interest2" /> Books</p> 
				<p><input type="checkbox" name="checkForm" id="interest4" /> Charity</p> 
				<p><input type="checkbox" name="checkForm" id="interest6" /> Entertainment</p> 
				<p><input type="checkbox" name="checkForm" id="interest8" /> Fashion</p> 
				<p><input type="checkbox" name="checkForm" id="interest10" /> Funny</p> 
				<p><input type="checkbox" name="checkForm" id="interest12" /> Music</p> 
				<p><input type="checkbox" name="checkForm" id="interest14" /> Politics</p> 
				<p><input type="checkbox" name="checkForm" id="interest16" /> Sports</p> 
				<p><input type="checkbox" name="checkForm" id="interest18" /> Vehicle</p> 
            </div> 
 * 
 * 
 */


public enum Interest {
	ART(0, "Art & Design"),
	BUSINESS(1, "Business"),
	DEALS(2, "Deals"),
	FAMILY(3, "Family"),
	FOOD(4, "Food"),
	HEALTH(5, "Health"),
	NEWS(6, "News"),
	SCITECH(7, "Sci & Tech"),
	BOOKS(8, "Books"),
	CHARITY(9, "Charity"),
	ENTERTAINMENT(10, "Entertainment"),
	FASHION(11, "Fashion"),
	FUNNY(12, "Funny"),
	MUSIC(13, "Music"),
	POLITICS(14, "Politics"),
	SPORTS(15, "Sports"),
	VEHICLE(16, "Vehicle");

	
	public int num;
	public String name;
	
	Interest(int num, String name) {
        this.num = num;
        this.name = name;
    }
	
	public static Interest findByNum(int num)
	{
		Interest result=null;
		for (Interest i: Interest.values())
		{
			
			if (i.num == num)
			{
				result = i;
				break;
			}
			
		}

		return result;
	}
}
