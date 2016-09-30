package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.model.Land;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for Grape Objects.
 * @author Da3mon
 *
 */
public interface GrapeRepository extends MongoRepository<Grape, String> {
	
	/**
	 * Returns a Grape based on id.
	 * @param id
	 * @return
	 */
	public Grape findById(String id);
	/**
	 * Returns a list of Grapes based on plantedOn. DOESNT WORK, will be fixed in
	 * Spring-Data MongoDB 1.1.0
	 * @param plantedOn
	 * @return
	 */
	public List<Grape> findByPlantedOn(Land plantedOn);

}
