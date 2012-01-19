package iyt.enums;

public enum Category {
	OTHER(0, "Other"),
	ART_DESIGN(1, "Art Design"),
	BOOKS(2, "Books"),
	BUSINESS(3, "Business"),
	CHARITY(4, "Charity"),
	DEALS(5, "Deals"),
	ENTERTAINMENT(6, "Entertainment"),
	FAMILY(7, "Family"),
	FASHION(8, "Fashion"),
	FOODDRINK(9, "Fooddrink"),
	FUNNY(10, "Funny"),
	HEALTH(11, "Health"),
	MUSIC(12, "Music"),
	NEWS(13, "News"),
	POLITICS(14, "Politics"),
	SCHTECH(15, "Schtech"),
	SPORTS(16, "Sports"),
	TRAVEL(17, "Travel"),
	VEHICLE(18, "Vehicle");
	
	int bit;
	String name;
	
	Category(int bit, String name) {
        this.bit = bit;
        this.name = name;
    }
	
	public static String toHTML() {
		String res = "";
		for(Category c : Category.values())
			res += "<option value=\"" + c.toString() + "\">" + c.name + "</option>\n";
		return res;
	}
}
