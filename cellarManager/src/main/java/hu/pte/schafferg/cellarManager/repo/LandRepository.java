package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LandRepository extends MongoRepository<Land, String> {
	
	public Land findById(String id);
	public List<Land> findByOwner(Person owner);

}
