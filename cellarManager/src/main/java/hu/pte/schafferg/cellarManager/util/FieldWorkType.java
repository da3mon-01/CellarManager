package hu.pte.schafferg.cellarManager.util;

/**
 * Enum of all FieldWork types
 * @author Da3mon
 *
 */
public enum FieldWorkType {
	INSECTSPRAY("Insect spraying"), 
	DUNGWORK("Dung work"), 
	POLLARDING("Pollarding"),  
	TENDRILREMOVAL("Tendril removal"), 
	SECTION("Section"), 
	BINDING("Binding"); 
	
	private final String name;

	private FieldWorkType(String name) {
		this.name = name;
	}
	
	public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }
	
	public String toString(){
	       return name;
	    }
}
