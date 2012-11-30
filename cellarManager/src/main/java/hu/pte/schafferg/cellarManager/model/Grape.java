package hu.pte.schafferg.cellarManager.model;



import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Grape {
	
	@Id
	private String id;
	private String type;
	private int quantity;
	private Date planted;
	@DBRef
	private Land plantedOn;
	
	@PersistenceConstructor
	public Grape(String id, String type, int quantity, Date planted, Land plantedOn) {
		this.id = id;
		this.type = type;
		this.quantity = quantity;
		this.planted = planted;
		this.plantedOn = plantedOn;
	}

	public Grape() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getPlanted() {
		return planted;
	}

	public void setPlanted(Date planted) {
		this.planted = planted;
	}

	public Land getPlantedOn() {
		return plantedOn;
	}

	public void setPlantedOn(Land plantedOn) {
		this.plantedOn = plantedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((planted == null) ? 0 : planted.hashCode());
		result = prime * result
				+ ((plantedOn == null) ? 0 : plantedOn.hashCode());
		result = prime * result + quantity;
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
		Grape other = (Grape) obj;
		if (planted == null) {
			if (other.planted != null)
				return false;
		} else if (!planted.equals(other.planted))
			return false;
		if (plantedOn == null) {
			if (other.plantedOn != null)
				return false;
		} else if (!plantedOn.equals(other.plantedOn))
			return false;
		if (quantity != other.quantity)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Grape [id=" + id + ", type=" + type + ", quantity=" + quantity
				+ ", planted=" + planted + ", plantedOn=" + plantedOn + "]";
	}
	
	

}
