package hu.pte.schafferg.cellarManager.repo;

import hu.pte.schafferg.cellarManager.model.GrapeMust;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GrapeMustRepository extends MongoRepository<GrapeMust, String> {
	
	public GrapeMust findById(String id);

}
