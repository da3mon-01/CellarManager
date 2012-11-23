package hu.pte.schafferg.cellarManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import hu.pte.schafferg.cellarManager.util.WineSweetness;

@Document
public class Wine {
	@Id
	private String id;
	private String obiNumber;
	private String type;
	private double alcoholDegree;
	private WineSweetness sweetness;
	@DBRef
	private GrapeMust madeFrom;
	@DBRef
	private Person bottler;
	private int numOfBottles;
	
	public Wine(String id, String obiNumber, String type, double alcoholDegree,
			WineSweetness sweetness, GrapeMust madeFrom, Person bottler,
			int numOfBottles) {
		this.id = id;
		this.obiNumber = obiNumber;
		this.type = type;
		this.alcoholDegree = alcoholDegree;
		this.sweetness = sweetness;
		this.madeFrom = madeFrom;
		this.bottler = bottler;
		this.numOfBottles = numOfBottles;
	}

	public Wine() {
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObiNumber() {
		return obiNumber;
	}

	public void setObiNumber(String obiNumber) {
		this.obiNumber = obiNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAlcoholDegree() {
		return alcoholDegree;
	}

	public void setAlcoholDegree(double alcoholDegree) {
		this.alcoholDegree = alcoholDegree;
	}

	public WineSweetness getSweetness() {
		return sweetness;
	}

	public void setSweetness(WineSweetness sweetness) {
		this.sweetness = sweetness;
	}

	public GrapeMust getMadeFrom() {
		return madeFrom;
	}

	public void setMadeFrom(GrapeMust madeFrom) {
		this.madeFrom = madeFrom;
	}

	public Person getBottler() {
		return bottler;
	}

	public void setBottler(Person bottler) {
		this.bottler = bottler;
	}

	public int getNumOfBottles() {
		return numOfBottles;
	}

	public void setNumOfBottles(int numOfBottles) {
		this.numOfBottles = numOfBottles;
	}
	
	
	

}

