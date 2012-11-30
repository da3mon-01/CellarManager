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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(extract);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(iron);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((must == null) ? 0 : must.hashCode());
		temp = Double.doubleToLongBits(sugar);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sulfur);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((when == null) ? 0 : when.hashCode());
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
		Analytic other = (Analytic) obj;
		if (Double.doubleToLongBits(extract) != Double
				.doubleToLongBits(other.extract))
			return false;
		if (Double.doubleToLongBits(iron) != Double
				.doubleToLongBits(other.iron))
			return false;
		if (must == null) {
			if (other.must != null)
				return false;
		} else if (!must.equals(other.must))
			return false;
		if (Double.doubleToLongBits(sugar) != Double
				.doubleToLongBits(other.sugar))
			return false;
		if (Double.doubleToLongBits(sulfur) != Double
				.doubleToLongBits(other.sulfur))
			return false;
		if (when == null) {
			if (other.when != null)
				return false;
		} else if (!when.equals(other.when))
			return false;
		return true;
	}
	
	

}
