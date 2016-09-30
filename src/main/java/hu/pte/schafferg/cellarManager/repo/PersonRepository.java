package hu.pte.schafferg.cellarManager.repo;



import hu.pte.schafferg.cellarManager.model.Person;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repository for Person objects
 * @author Da3mon
 *
 */
public interface PersonRepository extends MongoRepository<Person, String> {
	/**
	 * Returns a person based on id.
	 * @param id
	 * @return
	 */
	public Person findById(String id);

}
