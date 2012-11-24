package hu.pte.schafferg.cellarManager.services;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import hu.pte.schafferg.cellarManager.model.FieldWork;
import hu.pte.schafferg.cellarManager.model.Grape;
import hu.pte.schafferg.cellarManager.model.Land;
import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.repo.FieldWorkRepository;
import hu.pte.schafferg.cellarManager.repo.GrapeRepository;
import hu.pte.schafferg.cellarManager.repo.LandRepository;
import hu.pte.schafferg.cellarManager.repo.PersonRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

public class LandService {
	
	@Autowired
	public LandRepository landRepo;
	@Autowired
	public GrapeRepository grapeRepo;
	@Autowired
	public PersonRepository personRepo;
	@Autowired
	public FieldWorkRepository fieldWorkRepo;
	
	private static Logger logger = Logger.getLogger(LandService.class);
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void create(Land land) throws RuntimeException{
		
		land.setId(UUID.randomUUID().toString());
		
		personRepo.save(land.getOwner());
		Land created = landRepo.save(land);
		
		if(!created.equals(land)){
			throw new ObjectMisMatchException("The saved object does not match the original. Contact an Admin!");
		}
		
		logger.info("Land was created: "+created);
	}
	
	public Land read(Land land){
		return landRepo.findById(land.getId());
	}
	
	public List<Land> readAll(){
		return landRepo.findAll();
	}
	
	public List<Land> readAllFromOwner(Person owner){
		return landRepo.findByOwner(owner);
	}
	
	public List<Grape> readAllPlanetOn(Land land){
		return grapeRepo.findByPlantedOn(land);
	}
	
	public List<FieldWork> readAllFieldWorkDone(Land land){
		return fieldWorkRepo.findByOnWhat(land);
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void update(Land land) throws RuntimeException{
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
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public void delete(Land land) throws RuntimeException{
		Land landInDb = landRepo.findById(land.getId());
		
		if(landInDb == null){
			logger.info("Cannot find land in db: "+land);
			throw new TargetNotFoundInDBException("Could not find "+land.getLandOffId()+" in the database");
		}
		
		landRepo.delete(landInDb);
		
	}

}
