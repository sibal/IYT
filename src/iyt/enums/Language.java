package iyt.enums;

public enum Language {
	ENGLISH(0, "English", "English"),
	KOREAN(1, "Korean", "ÇÑ±¹¾î"),
	CHINESE(2, "Chinese", "ñéÙş"),
	JAPANESE(3, "Japanese", "ìíÜâåŞ");
	
	int num;
	String name_en, name;
	
	Language(int num, String name_en, String name) {
        this.num = num;
        this.name_en = name_en;
        this.name = name;
    }
}
