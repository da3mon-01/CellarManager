package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.repo.FieldWorkRepository;
import hu.pte.schafferg.cellarManager.repo.LandRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Service class for Fieldwork objects.
 * @author Da3mon
 *
 */
public class FieldWorkService {
	
	@Autowired
	private FieldWorkRepository fieldWorkRepo;
	@Autowired
	private LandRepository landRepo;
	private static Logger logger = Logger.getLogger(FieldWorkService.class);
	
	/**
	 * Creates a FieldWork in db.
	 * @param fieldWork
	 * @return
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public FieldWork create(FieldWork fieldWork){
		fieldWork.setId(UUID.randomUUID().toString());
		
		landRepo.save(fieldWork.getOnWhat());
		FieldWork created =fieldWorkRepo.save(fieldWork);
		
		if(!created.equals(fieldWork)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("FieldWork was created: "+created);
		
		return created;
	}
	
	/**
	 * Reads a FieldWork in db.
	 * @param fieldWork
	 * @return
	 */
	public FieldWork read(FieldWork fieldWork){
		return fieldWorkRepo.findById(fieldWork.getId());
	}
	
	/**
	 * Reads all FieldWork from db.
	 * @return
	 */
	public List<FieldWork> readAll(){
		return fieldWorkRepo.findAll();
	}
	
	/**
	 * Updates a FieldWork in db.
	 * @param fieldWork
	 * @return
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public FieldWork update(FieldWork fieldWork) throws RuntimeException{
		FieldWork fieldWorkInDb = fieldWorkRepo.findById(fieldWork.getId());
		
		if(fieldWorkInDb == null){
			logger.info("Cannot find person in db: "+fieldWork);
			throw new TargetNotFoundInDBException("Could not find "+fieldWork+" in the database");
		}
		
		fieldWorkInDb = fieldWork;
		landRepo.save(fieldWorkInDb.getOnWhat());
		fieldWorkInDb = fieldWorkRepo.save(fieldWorkInDb);
		
		if(!fieldWork.equals(fieldWorkInDb)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("FieldWork updated: "+fieldWorkInDb);
		
		return fieldWorkInDb;
	}
	
	/**
	 * Deletes a FieldWork from db.
	 * @param fieldWork
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void delete(FieldWork fieldWork) throws RuntimeException{
		FieldWork fieldWorkInDB = fieldWorkRepo.findById(fieldWork.getId());
		
		if(fieldWorkInDB == null){
			logger.info("Cannot find fieldwork in db: "+fieldWork);
			throw new TargetNotFoundInDBException("Could not find "+fieldWork+" in the database");
		}
		
		fieldWorkRepo.delete(fieldWorkInDB);
		logger.info("FieldWork deleted: "+fieldWorkInDB);
	}

	public FieldWorkRepository getFieldWorkRepo() {
		return fieldWorkRepo;
	}

	public void setFieldWorkRepo(FieldWorkRepository fieldWorkRepo) {
		this.fieldWorkRepo = fieldWorkRepo;
	}

	public LandRepository getLandRepo() {
		return landRepo;
	}

	public void setLandRepo(LandRepository landRepo) {
		this.landRepo = landRepo;
	}
	
	

}
