package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Sale;
import hu.pte.schafferg.cellarManager.model.Wine;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repository for Sale objects.
 * @author Da3mon
 *
 */
public interface SaleRepository extends MongoRepository<Sale, String> {
	/**
	 * Returns a Sale based on id.
	 * @param id
	 * @return
	 */
	public Sale findById(String id);
	/**
	 * Returns a list of Sales based on toWho. DOESNT WORK, will be fixed in
	 * Spring-Data MongoDB 1.1.0
	 * @param thoWho
	 * @return
	 */
	public List<Sale> findByToWho(Person thoWho);
	/**
	 * Returns a List of Sales based on what DOESNT WORK, will be fixed in
	 * Spring-Data MongoDB 1.1.0
	 * @param what
	 * @return
	 */
	public List<Sale> findByWhat(Wine what);

}
