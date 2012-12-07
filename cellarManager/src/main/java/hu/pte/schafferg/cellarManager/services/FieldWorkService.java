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

public class FieldWorkService {
	
	@Autowired
	private FieldWorkRepository fieldWorkRepo;
	@Autowired
	private LandRepository landRepo;
	private static Logger logger = Logger.getLogger(FieldWorkService.class);
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void create(FieldWork fieldWork){
		fieldWork.setId(UUID.randomUUID().toString());
		
		landRepo.save(fieldWork.getOnWhat());
		FieldWork created =fieldWorkRepo.save(fieldWork);
		
		if(!created.equals(fieldWork)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("FieldWork was created: "+created);
	}
	
	public FieldWork read(FieldWork fieldWork){
		return fieldWorkRepo.findById(fieldWork.getId());
	}
	
	public List<FieldWork> readAll(){
		return fieldWorkRepo.findAll();
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void update(FieldWork fieldWork) throws RuntimeException{
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
	}
	
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
