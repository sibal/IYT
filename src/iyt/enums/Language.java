package iyt.enums;

import java.util.List;
import java.util.ArrayList;

public enum Language {
	ENGLISH(0, "English", "English", "ENG"),
	KOREAN(1, "Korean", "Korean", "KOR"),
	CHINESE(2, "Chinese", "Chinese", "CHA"),
	JAPANESE(3, "Japanese", "Japanese", "JAP"),
	UNDEFINED(4, "Undefiend", "Undefiend", "UDF");
	
	public int num;
	public String name_en, name, abb_name;
	
	Language(int num, String name_en, String name, String abb_name) {
        this.num = num;
        this.name_en = name_en;
        this.name = name;
        this.abb_name = abb_name;
    }
	
	
	public static Language findByAbb(String abb)
	{
		Language result=null;
		for (Language i: Language.values())
		{
			
			if (i.abb_name.equals(abb))
			{
				result = i;
				break;
			}
			
		}
		
		if (result == null)
			return UNDEFINED;
		return result;
	}
	
	
	public static Language findByNum(int num)
	{
		Language result=null;
		for (Language i: Language.values())
		{
			
			if (i.num == num)
			{
				result = i;
				break;
			}
			
		}
		
		if (result == null)
			return UNDEFINED;
		return result;
	}
}
