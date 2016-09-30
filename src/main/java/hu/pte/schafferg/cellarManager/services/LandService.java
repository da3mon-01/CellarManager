package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.repo.FieldWorkRepository;
import hu.pte.schafferg.cellarManager.repo.GrapeRepository;
import hu.pte.schafferg.cellarManager.repo.LandRepository;
import hu.pte.schafferg.cellarManager.repo.PersonRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
/**
 * Service class for Land objects.
 * @author Da3mon
 *
 */
public class LandService {
	
	@Autowired
	private LandRepository landRepo;
	@Autowired
	private GrapeRepository grapeRepo;
	@Autowired
	private PersonRepository personRepo;
	@Autowired
	private FieldWorkRepository fieldWorkRepo;
	
	private static Logger logger = Logger.getLogger(LandService.class);
	
	/**
	 * Creates land in db.
	 * @param land
	 * @return
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Land create(Land land) throws RuntimeException{
		
		land.setId(UUID.randomUUID().toString());
		
		personRepo.save(land.getOwner());
		Land created = landRepo.save(land);
		
		if(!created.equals(land)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Land was created: "+created);
		
		return created;
	}
	
	/**
	 * Reads land from db.
	 * @param land
	 * @return
	 */
	public Land read(Land land){
		return landRepo.findById(land.getId());
	}
	
	/**
	 * Reads all lands in db.
	 * @return
	 */
	public List<Land> readAll(){
		return landRepo.findAll();
	}
	
	/**
	 * Gets all grapes planted on land.
	 * @param land
	 * @return
	 */
	public List<Grape> readAllPlanetOn(Land land){
		logger.info(land+"-> searching related Grapes");
		List<Grape> allGrapes = grapeRepo.findAll();
		List<Grape> result = new ArrayList<Grape>();
		
		for(Grape g: allGrapes){
			if(g.getPlantedOn().equals(land)){
				result.add(g);
			}
		}
		
		logger.info("Found: "+result);
		
		return result;
	}
	
	/**
	 * Gets all fieldwork done on land.
	 * @param land
	 * @return
	 */
	public List<FieldWork> readAllFieldWorkDone(Land land){
		logger.info(land+"-> searching related Fieldworks");
		List<FieldWork> allworks = fieldWorkRepo.findAll();
		List<FieldWork> result = new ArrayList<FieldWork>();
		
		for(FieldWork f: allworks){
			if(f.getOnWhat().equals(land)){
				result.add(f);
			}
		}
		
		logger.info("Found: "+result);
		
		return result;
	}
	
	/**
	 * Updates land in db.
	 * @param land
	 * @return
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Land update(Land land) throws RuntimeException{
		Land landInDb = landRepo.findById(land.getId());
		if(landInDb == null){
			logger.info("Cannot find land in db: "+land);
			throw new TargetNotFoundInDBException("Could not find "+land.getLandOffId()+" in the database");
		}
		landInDb = land;
		
		personRepo.save(landInDb.getOwner());
		landInDb = landRepo.save(land);
		
		if(!landInDb.equals(land)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Land updated: "+land.getLandOffId());
		
		return landInDb;
	}
	
	/**
	 * Deletes land from db.
	 * @param land
	 * @throws RuntimeException
	 */
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void delete(Land land) throws RuntimeException{
		Land landInDb = landRepo.findById(land.getId());
		
		if(landInDb == null){
			logger.info("Cannot find land in db: "+land);
			throw new TargetNotFoundInDBException("Could not find "+land.getLandOffId()+" in the database");
		}
		
		landRepo.delete(landInDb);
		
	}

	public LandRepository getLandRepo() {
		return landRepo;
	}

	public void setLandRepo(LandRepository landRepo) {
		this.landRepo = landRepo;
	}

	public GrapeRepository getGrapeRepo() {
		return grapeRepo;
	}

	public void setGrapeRepo(GrapeRepository grapeRepo) {
		this.grapeRepo = grapeRepo;
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
	
	

}
