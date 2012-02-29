package iyt.enums;

public enum Vender {
    FACEBOOK(0),
    TWITTER(1);
	
    private int bit;

    Vender(int bit) {
        this.bit = bit;
    }
    
    public static Vender getByInteger(int input)
    {
    	if (input == 0)
    		return FACEBOOK;
    	else
    		return TWITTER;
    }
}
