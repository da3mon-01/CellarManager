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

	@Override
	public String toString() {
		return "Wine [id=" + id + ", obiNumber=" + obiNumber + ", type=" + type
				+ ", alcoholDegree=" + alcoholDegree + ", sweetness="
				+ sweetness + ", madeFrom=" + madeFrom + ", bottler=" + bottler
				+ ", numOfBottles=" + numOfBottles + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(alcoholDegree);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((bottler == null) ? 0 : bottler.hashCode());
		result = prime * result
				+ ((madeFrom == null) ? 0 : madeFrom.hashCode());
		result = prime * result + numOfBottles;
		result = prime * result
				+ ((obiNumber == null) ? 0 : obiNumber.hashCode());
		result = prime * result
				+ ((sweetness == null) ? 0 : sweetness.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Wine other = (Wine) obj;
		if (Double.doubleToLongBits(alcoholDegree) != Double
				.doubleToLongBits(other.alcoholDegree))
			return false;
		if (bottler == null) {
			if (other.bottler != null)
				return false;
		} else if (!bottler.equals(other.bottler))
			return false;
		if (madeFrom == null) {
			if (other.madeFrom != null)
				return false;
		} else if (!madeFrom.equals(other.madeFrom))
			return false;
		if (numOfBottles != other.numOfBottles)
			return false;
		if (obiNumber == null) {
			if (other.obiNumber != null)
				return false;
		} else if (!obiNumber.equals(other.obiNumber))
			return false;
		if (sweetness != other.sweetness)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
	

}

