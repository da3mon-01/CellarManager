package hu.pte.schafferg.cellarManager.repo;

import hu.pte.schafferg.cellarManager.model.GrapeMust;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repository for GrapeMust objects.
 * @author Da3mon
 *
 */
public interface GrapeMustRepository extends MongoRepository<GrapeMust, String> {
	/**
	 * Returns a GrapeMust based on id.
	 * @param id
	 * @return
	 */
	public GrapeMust findById(String id);

}
