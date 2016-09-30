package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.repo.GrapeRepository;
import hu.pte.schafferg.cellarManager.repo.LandRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
/**
 * Service class for Grape Objects.
 * @author Da3mon
 *
 */
public class GrapeService {
	@Autowired
	private GrapeRepository grapeRepo;
	@Autowired
	private LandRepository landRepo;
	private static Logger logger = Logger.getLogger(GrapeService.class);
	
	/**
	 * Creates a grape in db.
	 * @param grape
	 * @return
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Grape create(Grape grape) throws RuntimeException{
		
		grape.setId(UUID.randomUUID().toString());
		
		landRepo.save(grape.getPlantedOn());
		Grape created =grapeRepo.save(grape);
		
		if(!created.equals(grape)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Grape was created: "+created);
		
		return created;
	}
	
	/**
	 * Reads a grape from db.
	 * @param grape
	 * @return
	 */
	public Grape read(Grape grape){
		return grapeRepo.findById(grape.getId());
	}
	
	/**
	 * Reads all grapes in db.
	 * @return
	 */
	public List<Grape> readAll(){
		return grapeRepo.findAll();
	}
	

	/**
	 * Updates grape in db.
	 * @param grape
	 * @return
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Grape update(Grape grape) throws RuntimeException{
		Grape grapeInDb = grapeRepo.findById(grape.getId());
		
		if(grapeInDb == null){
			logger.info("Cannot find person in db: "+grape);
			throw new TargetNotFoundInDBException("Could not find "+grape+" in the database");
		}
		
		grapeInDb = grape;
		landRepo.save(grapeInDb.getPlantedOn());
		grapeInDb = grapeRepo.save(grapeInDb);
		
		if(!grape.equals(grapeInDb)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Grape updated: "+grapeInDb);
		
		return grapeInDb;
	}
	
	/**
	 * Deletes grape from db
	 * @param grape
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void delete(Grape grape) throws RuntimeException{
		Grape grapeInDB = grapeRepo.findById(grape.getId());
		
		if(grapeInDB == null){
			logger.info("Cannot find grape in db: "+grape);
			throw new TargetNotFoundInDBException("Could not find "+grape+" in the database");
		}
		
		grapeRepo.delete(grapeInDB);
		logger.info("Grape deleted: "+grapeInDB);
	}

}
