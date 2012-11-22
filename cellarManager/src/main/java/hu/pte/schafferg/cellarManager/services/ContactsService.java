package hu.pte.schafferg.cellarManager.services;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import hu.pte.schafferg.cellarManager.model.Person;
import hu.pte.schafferg.cellarManager.repo.PersonRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;

public class ContactsService {
	
	@Autowired
	private PersonRepository personRepo;
	private static Logger logger = Logger.getLogger(ContactsService.class);
	
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
	

}
