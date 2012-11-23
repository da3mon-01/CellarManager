package hu.pte.schafferg.cellarManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sale {

	@Id
	private String id;
	@DBRef
	private Person toWho;
	@DBRef
	private Wine what;
	private int numOfBottles;
	private String wineDocID;
	
	public Sale(String id, Person toWho, Wine what, int numOfBottles,
			String wineDocID) {
		this.id = id;
		this.toWho = toWho;
		this.what = what;
		this.numOfBottles = numOfBottles;
		this.wineDocID = wineDocID;
	}

	public Sale() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Person getToWho() {
		return toWho;
	}

	public void setToWho(Person toWho) {
		this.toWho = toWho;
	}

	public Wine getWhat() {
		return what;
	}

	public void setWhat(Wine what) {
		this.what = what;
	}

	public int getNumOfBottles() {
		return numOfBottles;
	}

	public void setNumOfBottles(int numOfBottles) {
		this.numOfBottles = numOfBottles;
	}

	public String getWineDocID() {
		return wineDocID;
	}

	public void setWineDocID(String wineDocID) {
		this.wineDocID = wineDocID;
	}
	
	
	
	
}
