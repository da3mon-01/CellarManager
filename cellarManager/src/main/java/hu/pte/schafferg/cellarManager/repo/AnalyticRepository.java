package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.Analytic;
import hu.pte.schafferg.cellarManager.model.GrapeMust;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnalyticRepository extends MongoRepository<Analytic, String> {
	
	Analytic findById(String id);
	List<Analytic> findByMust(GrapeMust must);
}
