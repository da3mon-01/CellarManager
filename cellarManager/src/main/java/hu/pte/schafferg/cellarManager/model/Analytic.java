package hu.pte.schafferg.cellarManager.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Analytic {
	
	@Id
	private String id;
	private Date when;
	private double sulfur;
	private double sugar;
	private double iron;
	private double extract;
	@DBRef
	private GrapeMust must;
	
	@PersistenceConstructor
	public Analytic(String id, Date when, double sulfur, double sugar,
			double iron, double extract, GrapeMust must) {
		this.id = id;
		this.when = when;
		this.sulfur = sulfur;
		this.sugar = sugar;
		this.iron = iron;
		this.extract = extract;
		this.must = must;
	}

	public Analytic() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
	}

	public double getSulfur() {
		return sulfur;
	}

	public void setSulfur(double sulfur) {
		this.sulfur = sulfur;
	}

	public double getSugar() {
		return sugar;
	}

	public void setSugar(double sugar) {
		this.sugar = sugar;
	}

	public double getIron() {
		return iron;
	}

	public void setIron(double iron) {
		this.iron = iron;
	}

	public double getExtract() {
		return extract;
	}

	public void setExtract(double extract) {
		this.extract = extract;
	}

	public GrapeMust getMust() {
		return must;
	}

	public void setMust(GrapeMust must) {
		this.must = must;
	}
	
	

}
