package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repository for Land objects
 * @author Da3mon
 *
 */
public interface LandRepository extends MongoRepository<Land, String> {
	/**
	 * Returns a Land based on id.
	 * @param id
	 * @return
	 */
	public Land findById(String id);
	/**
	 * Returns a list of Land based on owner.
	 * @param owner
	 * @return
	 */
	public List<Land> findByOwner(Person owner);

}
