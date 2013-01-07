package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Wine;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repository for Wine objects.
 * @author Da3mon
 *
 */
public interface WineRepository extends MongoRepository<Wine, String> {
	/**
	 * Returns a Wine based on id.
	 * @param id
	 * @return
	 */
	public Wine findById(String id);
	/**
	 * Returns a list of Wines based on bottler. DOESNT WORK, will be fixed in
	 * Spring-Data MongoDB 1.1.0
	 * @param bottler
	 * @return
	 */
	public List<Wine> findByBottler(Person bottler);
	/**
	 * Returns a list of Wines based on madeFrom DOESNT WORK, will be fixed in
	 * Spring-Data MongoDB 1.1.0
	 * @param madeFrom
	 * @return
	 */
	public List<Wine> findByMadeFrom(GrapeMust madeFrom);

}
