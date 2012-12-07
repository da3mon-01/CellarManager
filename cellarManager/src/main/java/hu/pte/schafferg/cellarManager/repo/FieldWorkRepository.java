package hu.pte.schafferg.cellarManager.repo;

import java.util.List;


import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FieldWorkRepository extends MongoRepository<FieldWork, String> {
	
	FieldWork findById(String id);
	
	List<FieldWork> findByWho(Person who);
	
	List<FieldWork> findByOnWhat(Land onWhat);

}
