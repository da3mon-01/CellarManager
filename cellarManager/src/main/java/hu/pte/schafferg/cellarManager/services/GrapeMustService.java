package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.GrapeMust;
import hu.pte.schafferg.cellarManager.repo.GrapeMustRepository;
import hu.pte.schafferg.cellarManager.repo.GrapeRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

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
	private static Logger logger = Logger.getLogger(GrapeMustService.class);
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void create(GrapeMust grapeMust) throws RuntimeException{
		
		grapeMust.setId(UUID.randomUUID().toString());
		
		grapeRepo.save(grapeMust.getMadeFrom());
		GrapeMust created =grapeMustRepo.save(grapeMust);
		
		if(!created.equals(grapeMust)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("GrapeMust was created: "+created);
	}
	
	public GrapeMust read(GrapeMust grapeMust){
		return grapeMustRepo.findById(grapeMust.getId());
	}
	
	public List<GrapeMust> readAll(){
		return grapeMustRepo.findAll();
	}

	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void update(GrapeMust grapeMust) throws RuntimeException{
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

}
