package hu.pte.schafferg.cellarManager.model;

import java.util.List;

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
	@DBRef
	private List<Grape> grapes;
	
	@PersistenceConstructor
	public Land(String id, String landOffId, int size, Person owner,
			List<Grape> grapes) {
		this.id = id;
		this.landOffId = landOffId;
		this.size = size;
		this.owner = owner;
		this.grapes = grapes;
	}

	public Land() {
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public List<Grape> getGrapes() {
		return grapes;
	}

	public void setGrapes(List<Grape> grapes) {
		this.grapes = grapes;
	}
	
	
	
	

}
