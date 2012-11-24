package hu.pte.schafferg.cellarManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Land {
	@Id
	private String id;
	private String landOffId;
	private int size;
	@DBRef
	private Person owner;
	
	
	@PersistenceConstructor
	public Land(String id, String landOffId, int size, Person owner) {
		this.id = id;
		this.landOffId = landOffId;
		this.size = size;
		this.owner = owner;

	}
	
	public Land(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLandOffId() {
		return landOffId;
	}
	public void setLandOffId(String landOffId) {
		this.landOffId = landOffId;
	}
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	

}
