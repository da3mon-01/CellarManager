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
	
	
	

}
