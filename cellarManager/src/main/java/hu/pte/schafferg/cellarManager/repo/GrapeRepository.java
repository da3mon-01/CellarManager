package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.model.Land;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GrapeRepository extends MongoRepository<Grape, String> {
	
	public Grape findById(String id);
	public List<Grape> findByPlantedOn(Land plantedOn);

}
