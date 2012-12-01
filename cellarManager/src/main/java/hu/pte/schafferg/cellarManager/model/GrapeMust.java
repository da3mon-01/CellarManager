package hu.pte.schafferg.cellarManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GrapeMust {
	@Id
	private String id;
	@DBRef
	private Grape madeFrom;
	private double quantityAfterHarvest;
	private double quantityLostAfterRacking;
	private double mustDegree;
	private boolean enriched;
	private double enrichmentDegree;
	private boolean sweetened;
	
	@PersistenceConstructor
	public GrapeMust(String id, Grape madeFrom, double quantityAfterHarvest,
			double quantityLostAfterRacking, double mustDegree,
			boolean enriched, double enrichmentDegree, boolean sweetened) {
		this.id = id;
		this.madeFrom = madeFrom;
		this.quantityAfterHarvest = quantityAfterHarvest;
		this.quantityLostAfterRacking = quantityLostAfterRacking;
		this.mustDegree = mustDegree;
		this.enriched = enriched;
		this.enrichmentDegree = enrichmentDegree;
		this.sweetened = sweetened;
	}

	public GrapeMust() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Grape getMadeFrom() {
		return madeFrom;
	}

	public void setMadeFrom(Grape madeFrom) {
		this.madeFrom = madeFrom;
	}

	public double getQuantityAfterHarvest() {
		return quantityAfterHarvest;
	}

	public void setQuantityAfterHarvest(double quantityAfterHarvest) {
		this.quantityAfterHarvest = quantityAfterHarvest;
	}

	public double getQuantityLostAfterRacking() {
		return quantityLostAfterRacking;
	}

	public void setQuantityLostAfterRacking(double quantityLostAfterRacking) {
		this.quantityLostAfterRacking = quantityLostAfterRacking;
	}

	public double getMustDegree() {
		return mustDegree;
	}

	public void setMustDegree(double mustDegree) {
		this.mustDegree = mustDegree;
	}

	public boolean isEnriched() {
		return enriched;
	}

	public void setEnriched(boolean enriched) {
		this.enriched = enriched;
	}

	public double getEnrichmentDegree() {
		return enrichmentDegree;
	}

	public void setEnrichmentDegree(double enrichmentDegree) {
		this.enrichmentDegree = enrichmentDegree;
	}

	public boolean isSweetened() {
		return sweetened;
	}

	public void setSweetened(boolean sweetened) {
		this.sweetened = sweetened;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enriched ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(enrichmentDegree);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((madeFrom == null) ? 0 : madeFrom.hashCode());
		temp = Double.doubleToLongBits(mustDegree);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(quantityAfterHarvest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(quantityLostAfterRacking);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (sweetened ? 1231 : 1237);
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
		GrapeMust other = (GrapeMust) obj;
		if (enriched != other.enriched)
			return false;
		if (Double.doubleToLongBits(enrichmentDegree) != Double
				.doubleToLongBits(other.enrichmentDegree))
			return false;
		if (madeFrom == null) {
			if (other.madeFrom != null)
				return false;
		} else if (!madeFrom.equals(other.madeFrom))
			return false;
		if (Double.doubleToLongBits(mustDegree) != Double
				.doubleToLongBits(other.mustDegree))
			return false;
		if (Double.doubleToLongBits(quantityAfterHarvest) != Double
				.doubleToLongBits(other.quantityAfterHarvest))
			return false;
		if (Double.doubleToLongBits(quantityLostAfterRacking) != Double
				.doubleToLongBits(other.quantityLostAfterRacking))
			return false;
		if (sweetened != other.sweetened)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GrapeMust from: "+madeFrom;
	}
	
	

}
