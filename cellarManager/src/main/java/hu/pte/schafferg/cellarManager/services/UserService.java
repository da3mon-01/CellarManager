package hu.pte.schafferg.cellarManager.services;



import hu.pte.schafferg.cellarManager.model.User;
import hu.pte.schafferg.cellarManager.repo.PersonRepository;
import hu.pte.schafferg.cellarManager.repo.RoleRepository;
import hu.pte.schafferg.cellarManager.repo.UserRepository;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.StandardPasswordEncoder;



public class UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PersonRepository personRepo;
	private static Logger logger = Logger.getLogger(UserService.class);
	private static int randomPasswordLenght = 8;
	@Autowired
	private StandardPasswordEncoder sde;

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public User create(User user){
		user.setId(UUID.randomUUID().toString());
		user.getRole().setId(UUID.randomUUID().toString());
		user.getPerson().setId(UUID.randomUUID().toString());
		user.setPassword(sde.encode(generateRandomPassword()));
		user.getPerson().setUser(true);
		personRepo.save(user.getPerson());
		roleRepo.save(user.getRole());
		userRepo.save(user);
		logger.info(user.getUsername()+" was created");
		return user;
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN') or #user.username == authentication.name")
	public User read(User user){
		return user;
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN') or #username == authentication.name")
	public User readByUserName(String username) {
		return userRepo.findByUsername(username);
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<User> readAll(){
		return userRepo.findAll();
	}


	@PreAuthorize("hasAuthority('ROLE_ADMIN') or #user.username == authentication.name")
	public User update(User user){
		User existingUser = userRepo.findById(user.getId());

		if(existingUser == null){
			logger.info("Cannot Update null user");
			return null;
		}
		existingUser.setUsername(user.getUsername());
		existingUser.setPerson(user.getPerson());
		existingUser.setRole(user.getRole());
		existingUser.setPassword(user.getPassword());
		
		roleRepo.save(existingUser.getRole());
		personRepo.save(existingUser.getPerson());
		userRepo.save(existingUser);
		
		logger.info(existingUser.getUsername()+"was successfully updated");
		
		return existingUser;

	}
	
	public boolean resetPassword(User user){
		User existingUser = userRepo.findById(user.getId());
		
		if(existingUser == null){
			logger.info("Cannot Update null user");
			return false;
		}
		
		existingUser.setPassword(sde.encode(generateRandomPassword()));
		
		if(update(existingUser).equals(existingUser)){
			logger.info("Password Change successfull");
			return true;
		}else{
			return false;
		}
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
	
	public String generateRandomPassword(){
		Random rng = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i < randomPasswordLenght; i++){
			int random = rng.nextInt(60);
			if(random <= 25){
				sb.append( (char)(random+97) );
			}else if(random > 25 && random <= 50){
				sb.append( (char)((random-25)+65) );
			}else if(random > 60){
				sb.append(rng.nextInt(9));
			}
		}
		
		logger.info("Generated Password: "+sb.toString());
		return sb.toString();
	}


}
