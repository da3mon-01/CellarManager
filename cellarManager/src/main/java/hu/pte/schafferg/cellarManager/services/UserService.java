package hu.pte.schafferg.cellarManager.services;



import hu.pte.schafferg.cellarManager.model.User;
import hu.pte.schafferg.cellarManager.repo.PersonRepository;
import hu.pte.schafferg.cellarManager.repo.RoleRepository;
import hu.pte.schafferg.cellarManager.repo.UserRepository;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;



public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PersonRepository personRepo;
	private static Logger logger = Logger.getLogger(UserService.class);

	public User create(User user){
		user.setId(UUID.randomUUID().toString());
		user.getRole().setId(UUID.randomUUID().toString());
		user.getPerson().setId(UUID.randomUUID().toString());
		personRepo.save(user.getPerson());
		roleRepo.save(user.getRole());
		userRepo.save(user);

		return user;
	}

	public User read(User user){
		return user;
	}

	public User readByUserName(String username) {
		return userRepo.findByUsername(username);
	}

	public List<User> readAll(){
		return userRepo.findAll();
	}
	
	
	
	public User update(User user){
		User existingUser = userRepo.findById(user.getId());

		if(existingUser == null){
			return null;
		}

		if(existingUser.getPerson() != null){
			existingUser.getPerson().setFirstName(user.getPerson().getFirstName());
			existingUser.getPerson().setLastName(user.getPerson().getLastName());
			existingUser.getPerson().setCity(user.getPerson().getCity());
			existingUser.getPerson().setZip(user.getPerson().getZip());
			existingUser.getPerson().setAddress(user.getPerson().getAddress());
			existingUser.getPerson().setEmail(user.getPerson().getEmail());
			existingUser.getPerson().setBirthDate(user.getPerson().getBirthDate());
		}

		existingUser.setRole(user.getRole());

		existingUser.setPassword(user.getPassword());

		roleRepo.save(existingUser.getRole());
		personRepo.save(existingUser.getPerson());
		userRepo.save(existingUser);

		return existingUser;

	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public Boolean delete(User user){
		User existingUser = userRepo.findById(user.getId());
		
		if(existingUser == null){
			logger.debug("Cannot delete -> existingUser is null");
			return false;
		}
		logger.debug("Trying to delete: " +existingUser.getUsername());
			
		personRepo.delete(existingUser.getPerson());
		roleRepo.delete(existingUser.getRole());
		userRepo.delete(existingUser);

		return true;
	}


}
