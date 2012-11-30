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
	private String landOff;
	private int size;
	@DBRef
	private Person owner;
	
	
	@PersistenceConstructor
	public Land(String id, String landOffId, String landOff, int size, Person owner) {
		this.id = id;
		this.landOffId = landOffId;
		this.landOff = landOff;
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
	
	
	public String getLandOff() {
		return landOff;
	}

	public void setLandOff(String landOff) {
		this.landOff = landOff;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((landOff == null) ? 0 : landOff.hashCode());
		result = prime * result
				+ ((landOffId == null) ? 0 : landOffId.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + size;
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
		Land other = (Land) obj;
		if (landOff == null) {
			if (other.landOff != null)
				return false;
		} else if (!landOff.equals(other.landOff))
			return false;
		if (landOffId == null) {
			if (other.landOffId != null)
				return false;
		} else if (!landOffId.equals(other.landOffId))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Land [id=" + id + ", landOffId=" + landOffId + ", landOff="
				+ landOff + ", size=" + size + ", owner=" + owner + "]";
	}
	
	
	
}
