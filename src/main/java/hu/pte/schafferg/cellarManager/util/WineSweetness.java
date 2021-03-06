package hu.pte.schafferg.cellarManager.util;

/**
 * Enum all wine-sweetness levels.
 * @author Da3mon
 *
 */
public enum WineSweetness {
	SWEET("Sweet"), 
	MEDIUM("Medium"), 
	MEDIUMDRY("Medium-dry"), 
	DRY("Dry");
	
	private final String sweetness;

	private WineSweetness(String sweetness) {
		this.sweetness = sweetness;
	}
	
	public boolean equalsSweetness(String otherSweetness){
        return (otherSweetness == null)? false:sweetness.equals(otherSweetness);
    }
	
	public String toString(){
		return sweetness;
	}
	
	
	
	
}
