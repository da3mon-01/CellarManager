package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.Sale;
import hu.pte.schafferg.cellarManager.repo.PersonRepository;
import hu.pte.schafferg.cellarManager.repo.SaleRepository;
import hu.pte.schafferg.cellarManager.repo.WineRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
/**
 * Service class for Sale Objects
 * @author Da3mon
 *
 */
public class SaleService {
	
	@Autowired
	private WineRepository wineRepo;
	@Autowired
	private PersonRepository personRepo;
	@Autowired
	private SaleRepository saleRepo;
	private static Logger logger = Logger.getLogger(SaleService.class);
	
	/**
	 * Creates sale in db
	 * @param sale
	 * @return
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Sale create(Sale sale) throws RuntimeException{
		
		sale.setId(UUID.randomUUID().toString());
		
		wineRepo.save(sale.getWhat());
		personRepo.save(sale.getToWho());
		Sale created=saleRepo.save(sale);
		
		if(!created.equals(sale)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Sale was created: "+created);
		
		return created;
	}
	
	/**
	 * Reads sale from db.
	 * @param sale
	 * @return
	 */
	public Sale read(Sale sale){
		return saleRepo.findById(sale.getId());
	}
	
	/**
	 * Reads all sales from db.
	 * @return
	 */
	public List<Sale> readAll(){
		return saleRepo.findAll();
	}
	

	/**
	 * Updates sale in db.
	 * @param sale
	 * @return
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Sale update(Sale sale) throws RuntimeException{
		Sale saleInDb = saleRepo.findById(sale.getId());
		
		if(saleInDb == null){
			logger.info("Cannot find person in db: "+sale);
			throw new TargetNotFoundInDBException("Could not find "+sale+" in the database");
		}
		
		saleInDb = sale;
		wineRepo.save(saleInDb.getWhat());
		personRepo.save(saleInDb.getToWho());
		saleInDb = saleRepo.save(saleInDb);
		
		if(!sale.equals(saleInDb)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Sale updated: "+saleInDb);
		
		return saleInDb;
	}
	
	/**
	 * Deletes sale from db.
	 * @param sale
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void delete(Sale sale) throws RuntimeException{
		Sale saleInDB = saleRepo.findById(sale.getId());
		
		if(saleInDB == null){
			logger.info("Cannot find wine in db: "+sale);
			throw new TargetNotFoundInDBException("Could not find "+sale+" in the database");
		}
		
		saleRepo.delete(saleInDB);
		logger.info("Sale deleted: "+saleInDB);
	}

}
