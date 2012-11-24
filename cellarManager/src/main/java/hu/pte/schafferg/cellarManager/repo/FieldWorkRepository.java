package hu.pte.schafferg.cellarManager.repo;

import java.util.List;


import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FieldWorkRepository extends MongoRepository<FieldWork, String> {
	
	public FieldWork findById(String id);
	
	public List<FieldWork> findByWho(Person who);
	
	public List<FieldWork> findByOnWhat(Land onWhat);

}
