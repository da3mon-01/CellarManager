package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.Sale;
import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.repo.GrapeMustRepository;
import hu.pte.schafferg.cellarManager.repo.PersonRepository;
import hu.pte.schafferg.cellarManager.repo.SaleRepository;
import hu.pte.schafferg.cellarManager.repo.WineRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

import java.util.ArrayList;
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
	private SaleRepository saleRepo;
	@Autowired
	private PersonRepository personRepo;
	private static Logger logger = Logger.getLogger(WineService.class);
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Wine create(Wine wine) throws RuntimeException{
		
		wine.setId(UUID.randomUUID().toString());
		
		grapeMustRepo.save(wine.getMadeFrom());
		personRepo.save(wine.getBottler());
		Wine created =wineRepo.save(wine);
		
		if(!created.equals(wine)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Wine was created: "+created);
		
		return created;
	}
	
	public Wine read(Wine wine){
		return wineRepo.findById(wine.getId());
	}
	
	public List<Wine> readAll(){
		return wineRepo.findAll();
	}
	
	public List<Sale> getAllSales(Wine wine){
		logger.info(wine+"-> searching related Sales");
		List<Sale> allSales = saleRepo.findAll();
		List<Sale> result = new ArrayList<Sale>();
		
		for(Sale s: allSales){
			if(s.getWhat().equals(wine)){
				result.add(s);
			}
		}
		
		logger.info("Found: "+result);
		return result;
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Wine update(Wine wine) throws RuntimeException{
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
		
		return wineInDb;
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

	public WineRepository getWineRepo() {
		return wineRepo;
	}

	public void setWineRepo(WineRepository wineRepo) {
		this.wineRepo = wineRepo;
	}

	public GrapeMustRepository getGrapeMustRepo() {
		return grapeMustRepo;
	}

	public void setGrapeMustRepo(GrapeMustRepository grapeMustRepo) {
		this.grapeMustRepo = grapeMustRepo;
	}

	public SaleRepository getSaleRepo() {
		return saleRepo;
	}

	public void setSaleRepo(SaleRepository saleRepo) {
		this.saleRepo = saleRepo;
	}

	public PersonRepository getPersonRepo() {
		return personRepo;
	}

	public void setPersonRepo(PersonRepository personRepo) {
		this.personRepo = personRepo;
	}

	
}
