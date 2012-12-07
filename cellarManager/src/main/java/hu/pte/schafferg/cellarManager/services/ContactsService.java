package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.model.Sale;
import hu.pte.schafferg.cellarManager.model.Wine;
import hu.pte.schafferg.cellarManager.repo.FieldWorkRepository;
import hu.pte.schafferg.cellarManager.repo.LandRepository;
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

public class ContactsService {
	
	@Autowired
	private PersonRepository personRepo;
	private static Logger logger = Logger.getLogger(ContactsService.class);
	@Autowired
	private FieldWorkRepository fieldWorkRepo;
	@Autowired
	private LandRepository landRepo;
	@Autowired
	private SaleRepository saleRepo;
	@Autowired
	private WineRepository wineRepo;
	
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void create(Person person) throws RuntimeException{
		
		person.setId(UUID.randomUUID().toString());
		
		Person created = personRepo.save(person);
		
		if(!person.equals(created)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Person: "+person+" was created");
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Person read(Person person){
		return personRepo.findById(person.getId());
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public List<Person> readAll(){
		return personRepo.findAll();
	}
	
	public List<FieldWork> getAllFieldWorkDone(Person person){
		logger.info(person+"-> searching related Fieldworks");
		List<FieldWork> allworks = fieldWorkRepo.findAll();
		List<FieldWork> result = new ArrayList<FieldWork>();
		
		for(FieldWork f: allworks){
			if(f.getWho().equals(person)){
				result.add(f);
			}
		}
		
		logger.info("Found: "+result);
		
		return result;
	}
	
	public List<Land> getAllLandsOwned(Person person){
		logger.info(person+"-> searching related Lands");
		List<Land> allLands = landRepo.findAll();
		List<Land> result = new ArrayList<Land>();
		
		for(Land l: allLands){
			if(l.getOwner().equals(person)){
				result.add(l);
			}
		}
		
		logger.info("Found: "+result);
		return result;
	}
	
	public List<Sale> getAllSales(Person person){
		logger.info(person+"-> searching related Sales");
		List<Sale> allSales = saleRepo.findAll();
		List<Sale> result = new ArrayList<Sale>();
		
		for(Sale s: allSales){
			if(s.getToWho().equals(person)){
				result.add(s);
			}
		}
		
		logger.info("Found: "+result);
		return result;
	}
	
	public List<Wine> getAllWinesBottled(Person person){
		logger.info(person+"-> searching related Sales");
		List<Wine> allWines = wineRepo.findAll();
		List<Wine> result = new ArrayList<Wine>();
		
		for(Wine w: allWines){
			if(w.getBottler().equals(person)){
				result.add(w);
			}
		}
		
		logger.info("Found: "+result);
		return result;
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void update(Person person) throws RuntimeException {
		Person personInDB = personRepo.findById(person.getId());
		
		if(personInDB == null){
			logger.info("Cannot find person in db: "+person);
			throw new TargetNotFoundInDBException("Could not find "+person.getFirstName()+" "+person.getLastName()+" in the database");
		}
		
		personInDB = person;
		
		personInDB = personRepo.save(personInDB);
		
		if(!person.equals(personInDB)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Person updated: "+personInDB);
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void delete(Person person) throws RuntimeException{
		Person personInDB = personRepo.findById(person.getId());
		
		if(personInDB == null){
			logger.info("Cannot find person in db: "+person);
			throw new TargetNotFoundInDBException("Could not find "+person.getFirstName()+" "+person.getLastName()+" in the database");
		}
		
		personRepo.delete(personInDB);
		logger.info("Person deleted: "+personInDB);
	}
	
	
	public PersonRepository getPersonRepo() {
		return personRepo;
	}
	public void setPersonRepo(PersonRepository personRepo) {
		this.personRepo = personRepo;
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

	public SaleRepository getSaleRepo() {
		return saleRepo;
	}

	public void setSaleRepo(SaleRepository saleRepo) {
		this.saleRepo = saleRepo;
	}

	public WineRepository getWineRepo() {
		return wineRepo;
	}

	public void setWineRepo(WineRepository wineRepo) {
		this.wineRepo = wineRepo;
	}
	
	
	

}
