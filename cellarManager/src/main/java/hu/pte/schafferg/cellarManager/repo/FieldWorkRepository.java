package hu.pte.schafferg.cellarManager.repo;

import java.util.List;


import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repository for FieldWork classes.
 * @author Da3mon
 *
 */
public interface FieldWorkRepository extends MongoRepository<FieldWork, String> {
	
	/**
	 * Returns a FieldWork based on id.
	 * @param id
	 * @return
	 */
	FieldWork findById(String id);
	
	/**
	 * Returns a FieldWork based on who. DOESNT WORK, will be fixed in
	 * Spring-Data MongoDB 1.1.0
	 * @param who
	 * @return
	 */
	List<FieldWork> findByWho(Person who);
	/**
	 * Returns a FieldWork based on what. DOESNT WORK, will be fixed in
	 * Spring-Data MongoDB 1.1.0
	 * @param onWhat
	 * @return
	 */
	List<FieldWork> findByOnWhat(Land onWhat);

}
