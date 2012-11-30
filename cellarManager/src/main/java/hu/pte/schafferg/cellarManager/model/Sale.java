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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numOfBottles;
		result = prime * result + ((toWho == null) ? 0 : toWho.hashCode());
		result = prime * result + ((what == null) ? 0 : what.hashCode());
		result = prime * result
				+ ((wineDocID == null) ? 0 : wineDocID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		if (numOfBottles != other.numOfBottles)
			return false;
		if (toWho == null) {
			if (other.toWho != null)
				return false;
		} else if (!toWho.equals(other.toWho))
			return false;
		if (what == null) {
			if (other.what != null)
				return false;
		} else if (!what.equals(other.what))
			return false;
		if (wineDocID == null) {
			if (other.wineDocID != null)
				return false;
		} else if (!wineDocID.equals(other.wineDocID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", toWho=" + toWho + ", what=" + what
				+ ", numOfBottles=" + numOfBottles + ", wineDocID=" + wineDocID
				+ "]";
	}
	
	
		
	
}
