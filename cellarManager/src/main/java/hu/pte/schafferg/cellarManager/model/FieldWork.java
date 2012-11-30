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
	@DBRef
	private Person who;
	private Date when;
	private FieldWorkType work;
	@DBRef
	private Land onWhat;
	
	
	@PersistenceConstructor
	public FieldWork(String id, Person who, Date when, FieldWorkType work, Land onWhat) {
		this.id = id;
		this.who = who;
		this.when = when;
		this.work = work;
		this.onWhat = onWhat;
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


	public FieldWorkType getWork() {
		return work;
	}


	public void setWork(FieldWorkType work) {
		this.work = work;
	}


	public Land getOnWhat() {
		return onWhat;
	}


	public void setOnWhat(Land onWhat) {
		this.onWhat = onWhat;
	}


	@Override
	public String toString() {
		return "FieldWork [id=" + id + ", who=" + who + ", when=" + when
				+ ", work=" + work + ", onWhat=" + onWhat + "]";
	}
	
	
	

}
