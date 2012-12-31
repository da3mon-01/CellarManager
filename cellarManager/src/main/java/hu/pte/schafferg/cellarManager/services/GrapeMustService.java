package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.Analytic;
import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.repo.AnalyticRepository;
import hu.pte.schafferg.cellarManager.repo.GrapeMustRepository;
import hu.pte.schafferg.cellarManager.repo.GrapeRepository;
import hu.pte.schafferg.cellarManager.repo.WineRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

public class GrapeMustService {
	
	@Autowired
	private GrapeMustRepository grapeMustRepo;
	@Autowired
	private GrapeRepository grapeRepo;
	@Autowired
	private AnalyticRepository analyticRepo;
	@Autowired
	private WineRepository wineRepo;
	private static Logger logger = Logger.getLogger(GrapeMustService.class);
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public GrapeMust create(GrapeMust grapeMust) throws RuntimeException{
		
		grapeMust.setId(UUID.randomUUID().toString());
		
		grapeRepo.save(grapeMust.getMadeFrom());
		GrapeMust created =grapeMustRepo.save(grapeMust);
		
		if(!created.equals(grapeMust)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("GrapeMust was created: "+created);
		return created;
	}
	
	public GrapeMust read(GrapeMust grapeMust){
		return grapeMustRepo.findById(grapeMust.getId());
	}
	
	public List<GrapeMust> readAll(){
		return grapeMustRepo.findAll();
	}
	
	public List<Analytic> getAllAnalytics(GrapeMust must){
		logger.info(must+"-> searching related Analytics");
		List<Analytic> allAnalytic = analyticRepo.findAll();
		List<Analytic> result = new ArrayList<Analytic>();
		
		for(Analytic a: allAnalytic){
			if(a.getMust().equals(must)){
				result.add(a);
			}
		}
		
		logger.info("Found: "+result);
		
		return result;
	}
	
	public List<Wine> getAllWineMade(GrapeMust must){
		logger.info(must+"-> searching related Analytics");
		List<Wine> allwines = wineRepo.findAll();
		List<Wine> result = new ArrayList<Wine>();
		
		for(Wine w: allwines){
			if(w.getMadeFrom().equals(must)){
				result.add(w);
			}
		}
		
		logger.info("Found: "+result);
		
		return result;
	}

	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public GrapeMust update(GrapeMust grapeMust) throws RuntimeException{
		GrapeMust grapeMustInDb = grapeMustRepo.findById(grapeMust.getId());
		
		if(grapeMustInDb == null){
			logger.info("Cannot find person in db: "+grapeMust);
			throw new TargetNotFoundInDBException("Could not find "+grapeMust+" in the database");
		}
		
		grapeMustInDb = grapeMust;
		grapeRepo.save(grapeMustInDb.getMadeFrom());
		grapeMustInDb = grapeMustRepo.save(grapeMustInDb);
		
		if(!grapeMust.equals(grapeMustInDb)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("GrapeMust updated: "+grapeMustInDb);
		
		return grapeMustInDb;
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void delete(GrapeMust grapeMust) throws RuntimeException{
		GrapeMust grapeMustInDB = grapeMustRepo.findById(grapeMust.getId());
		
		if(grapeMustInDB == null){
			logger.info("Cannot find grapemust in db: "+grapeMust);
			throw new TargetNotFoundInDBException("Could not find "+grapeMust+" in the database");
		}
		
		grapeMustRepo.delete(grapeMustInDB);
		logger.info("GrapeMust deleted: "+grapeMustInDB);
	}

	public GrapeMustRepository getGrapeMustRepo() {
		return grapeMustRepo;
	}

	public void setGrapeMustRepo(GrapeMustRepository grapeMustRepo) {
		this.grapeMustRepo = grapeMustRepo;
	}

	public GrapeRepository getGrapeRepo() {
		return grapeRepo;
	}

	public void setGrapeRepo(GrapeRepository grapeRepo) {
		this.grapeRepo = grapeRepo;
	}

	public AnalyticRepository getAnalyticRepo() {
		return analyticRepo;
	}

	public void setAnalyticRepo(AnalyticRepository analyticRepo) {
		this.analyticRepo = analyticRepo;
	}

	public WineRepository getWineRepo() {
		return wineRepo;
	}

	public void setWineRepo(WineRepository wineRepo) {
		this.wineRepo = wineRepo;
	}
	
	

}
