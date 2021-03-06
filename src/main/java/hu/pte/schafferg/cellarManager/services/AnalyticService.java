package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.Analytic;
import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.repo.AnalyticRepository;
import hu.pte.schafferg.cellarManager.repo.GrapeMustRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
/**
 * Service class for Analytic Objects
 * @author Da3mon
 *
 */
public class AnalyticService {
	
	@Autowired
	private AnalyticRepository analyticRepo;
	@Autowired
	private GrapeMustRepository grapeMustRepo;
	private static Logger logger = Logger.getLogger(AnalyticService.class);
	
	/**
	 * Creates an Analytic in db.
	 * @param analytic
	 * @return
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Analytic create(Analytic analytic) throws RuntimeException{
		
		analytic.setId(UUID.randomUUID().toString());
		
		grapeMustRepo.save(analytic.getMust());
		Analytic created =analyticRepo.save(analytic);
		
		if(!created.equals(analytic)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Analytic was created: "+created);
		
		return created;
	}
	
	/**
	 * Reads an Analytic from db.
	 * @param analytic
	 * @return
	 */
	public Analytic read(Analytic analytic){
		return analyticRepo.findById(analytic.getId());
	}
	
	/**
	 * Reads all analytics from db.
	 * @return
	 */
	public List<Analytic> readAll(){
		return analyticRepo.findAll();
	}
	/**
	 * Returns a list of analytics based on must. DOESNT WORK, will be fixed in
	 * Spring-Data MongoDB 1.1.0
	 * @param must
	 * @return
	 */
	public List<Analytic> readAllFromAMust(GrapeMust must){
		return analyticRepo.findByMust(must);
	}
	
	/**
	 * Updates an analytic in db.
	 * @param analytic
	 * @return
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Analytic update(Analytic analytic) throws RuntimeException{
		Analytic analyticInDb = analyticRepo.findById(analytic.getId());
		
		if(analyticInDb == null){
			logger.info("Cannot find person in db: "+analytic);
			throw new TargetNotFoundInDBException("Could not find "+analytic+" in the database");
		}
		
		analyticInDb = analytic;
		grapeMustRepo.save(analyticInDb.getMust());
		analyticInDb = analyticRepo.save(analyticInDb);
		
		if(!analytic.equals(analyticInDb)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Analytic updated: "+analyticInDb);
		
		return analyticInDb;
	}
	
	/**
	 * Deletes an analytic from db
	 * @param analytic
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void delete(Analytic analytic) throws RuntimeException{
		Analytic analyticInDB = analyticRepo.findById(analytic.getId());
		
		if(analyticInDB == null){
			logger.info("Cannot find Analytic in db: "+analytic);
			throw new TargetNotFoundInDBException("Could not find "+analytic+" in the database");
		}
		
		analyticRepo.delete(analyticInDB);
		logger.info("Analytic deleted: "+analyticInDB);
	}

	public AnalyticRepository getAnalyticRepo() {
		return analyticRepo;
	}

	public void setAnalyticRepo(AnalyticRepository analyticRepo) {
		this.analyticRepo = analyticRepo;
	}

	public GrapeMustRepository getGrapeMustRepo() {
		return grapeMustRepo;
	}

	public void setGrapeMustRepo(GrapeMustRepository grapeMustRepo) {
		this.grapeMustRepo = grapeMustRepo;
	}
	
	

}
