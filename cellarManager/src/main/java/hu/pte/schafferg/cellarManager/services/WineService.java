package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.repo.GrapeMustRepository;
import hu.pte.schafferg.cellarManager.repo.PersonRepository;
import hu.pte.schafferg.cellarManager.repo.WineRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

public class WineService {
	
	@Autowired
	private WineRepository wineRepo;
	@Autowired
	private GrapeMustRepository grapeMustRepo;
	@Autowired
	private PersonRepository personRepo;
	private static Logger logger = Logger.getLogger(WineService.class);
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void create(Wine wine) throws RuntimeException{
		
		wine.setId(UUID.randomUUID().toString());
		
		grapeMustRepo.save(wine.getMadeFrom());
		personRepo.save(wine.getBottler());
		Wine created =wineRepo.save(wine);
		
		if(!created.equals(wine)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Wine was created: "+created);
	}
	
	public Wine read(Wine wine){
		return wineRepo.findById(wine.getId());
	}
	
	public List<Wine> readAll(){
		return wineRepo.findAll();
	}
	
	public List<Wine> readAllFromABottler(Person bottler){
		return wineRepo.findByBottler(bottler);
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void update(Wine wine) throws RuntimeException{
		Wine wineInDb = wineRepo.findById(wine.getId());
		
		if(wineInDb == null){
			logger.info("Cannot find person in db: "+wine);
			throw new TargetNotFoundInDBException("Could not find "+wine+" in the database");
		}
		
		wineInDb = wine;
		grapeMustRepo.save(wineInDb.getMadeFrom());
		personRepo.save(wineInDb.getBottler());
		wineInDb = wineRepo.save(wineInDb);
		
		if(!wine.equals(wineInDb)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Wine updated: "+wineInDb);
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void delete(Wine wine) throws RuntimeException{
		Wine wineInDB = wineRepo.findById(wine.getId());
		
		if(wineInDB == null){
			logger.info("Cannot find wine in db: "+wine);
			throw new TargetNotFoundInDBException("Could not find "+wine+" in the database");
		}
		
		wineRepo.delete(wineInDB);
		logger.info("Wine deleted: "+wineInDB);
	}

}