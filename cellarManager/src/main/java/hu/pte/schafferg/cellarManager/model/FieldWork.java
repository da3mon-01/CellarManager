package hu.pte.schafferg.cellarManager.model;

import hu.pte.schafferg.cellarManager.util.FieldWorkType;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FieldWork {
	
	@Id
	private String id;
	private Person who;
	private Date when;
	@DBRef
	private Land onWhat;
	private FieldWorkType work;
	
	@PersistenceConstructor
	public FieldWork(String id, Person who, Date when, Land onWhat,
			FieldWorkType work) {
		this.id = id;
		this.who = who;
		this.when = when;
		this.onWhat = onWhat;
		this.work = work;
	}

	public FieldWork() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Person getWho() {
		return who;
	}

	public void setWho(Person who) {
		this.who = who;
	}

	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
	}

	public Land getOnWhat() {
		return onWhat;
	}

	public void setOnWhat(Land onWhat) {
		this.onWhat = onWhat;
	}

	public FieldWorkType getWork() {
		return work;
	}

	public void setWork(FieldWorkType work) {
		this.work = work;
	}
	
	
	
	

}
