package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Wine;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WineRepository extends MongoRepository<Wine, String> {
	
	public Wine findById(String id);
	public List<Wine> findByBottler(Person bottler);
	public List<Wine> findByMadeFrom(GrapeMust madeFrom);

}
