package hu.pte.schafferg.cellarManager.repo;

import java.util.List;

import hu.pte.schafferg.cellarManager.model.Analytic;
import hu.pte.schafferg.cellarManager.model.GrapeMust;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnalyticRepository extends MongoRepository<Analytic, String> {
	
	public Analytic findById(String id);
	public List<Analytic> findByMust(GrapeMust must);
}
