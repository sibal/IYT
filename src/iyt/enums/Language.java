package iyt.enums;

public enum Language {
	ENGLISH(0, "English", "English"),
	KOREAN(1, "Korean", "Korean"),
	CHINESE(2, "Chinese", "Chinese"),
	JAPANESE(3, "Japanese", "Japanese");
	
	int num;
	String name_en, name;
	
	Language(int num, String name_en, String name) {
        this.num = num;
        this.name_en = name_en;
        this.name = name;
    }
}
