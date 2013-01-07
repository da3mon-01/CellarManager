package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.Analytic;
import hu.pte.schafferg.cellarManager.model.GrapeMust;

import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * 
 * @author Da3mon
 *	Repository for Analytics
 */
public interface AnalyticRepository extends MongoRepository<Analytic, String> {
	
	/**
	 * Returns an Analytic based on ID.
	 * @param id
	 * @return
	 */
	Analytic findById(String id);
	
	/**
	 * Returns a list of analytics based on must. DOESNT WORK, will be fixed in
	 * Spring-Data MongoDB 1.1.0
	 * @param must
	 * @return
	 */
	List<Analytic> findByMust(GrapeMust must);
}
