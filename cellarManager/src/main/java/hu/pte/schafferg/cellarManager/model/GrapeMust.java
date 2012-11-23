package hu.pte.schafferg.cellarManager.model;

import java.util.List;

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
	private double enrichtmentDegree;
	private boolean sweetened;
	private List<Analytic> analytics;
	
	@PersistenceConstructor
	public GrapeMust(String id, Grape madeFrom, double quantityAfterHarvest,
			double quantityLostAfterRacking, double mustDegree,
			boolean enriched, double enrichtmentDegree, boolean sweetened,
			List<Analytic> analytics) {
		this.id = id;
		this.madeFrom = madeFrom;
		this.quantityAfterHarvest = quantityAfterHarvest;
		this.quantityLostAfterRacking = quantityLostAfterRacking;
		this.mustDegree = mustDegree;
		this.enriched = enriched;
		this.enrichtmentDegree = enrichtmentDegree;
		this.sweetened = sweetened;
		this.analytics = analytics;
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

	public double getEnrichtmentDegree() {
		return enrichtmentDegree;
	}

	public void setEnrichtmentDegree(double enrichtmentDegree) {
		this.enrichtmentDegree = enrichtmentDegree;
	}

	public boolean isSweetened() {
		return sweetened;
	}

	public void setSweetened(boolean sweetened) {
		this.sweetened = sweetened;
	}

	public List<Analytic> getAnalytics() {
		return analytics;
	}

	public void setAnalytics(List<Analytic> analytics) {
		this.analytics = analytics;
	}
	
	
	

}
