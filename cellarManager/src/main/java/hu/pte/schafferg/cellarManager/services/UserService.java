package hu.pte.schafferg.cellarManager.services;



import hu.pte.schafferg.cellarManager.model.User;
import hu.pte.schafferg.cellarManager.repo.PersonRepository;
import hu.pte.schafferg.cellarManager.repo.RoleRepository;
import hu.pte.schafferg.cellarManager.repo.UserRepository;
import hu.pte.schafferg.cellarManager.util.ObjectMisMatchException;
import hu.pte.schafferg.cellarManager.util.TargetNotFoundInDBException;
import hu.pte.schafferg.cellarManager.util.UserNameInUseException;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;

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
	@Autowired
	EmailService mailService;
	


	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public User create(User user) throws MessagingException{
		User userToCreate = user;
		
		userToCreate.setId(UUID.randomUUID().toString());
		userToCreate.getRole().setId(UUID.randomUUID().toString());
		userToCreate.getPerson().setId(UUID.randomUUID().toString());
		String newpass = generateRandomPassword();
		userToCreate.setPassword(sde.encode(newpass));
		userToCreate.getPerson().setUser(true);
		
		List<User> currentUsers = userRepo.findAll();
		for(User u: currentUsers){
			if(u.getUsername().equals(user.getUsername())){
				throw new UserNameInUseException("The username "+user.getUsername()+" is already in use!");
			}
		}		
		
		
		personRepo.save(user.getPerson());
		roleRepo.save(user.getRole());
		userToCreate = userRepo.save(user);
		
		if(!(user.equals(userToCreate))){
			throw new ObjectMisMatchException("There was an Object-mismatch error while updating "+user.getUsername()+". Please check the database for irreguralities");
		}
		
		try {
			mailService.sendCreateUserMail(userToCreate, newpass);
		} catch (MessagingException e) {
			throw e;
		}
		
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
			throw new TargetNotFoundInDBException("User could not be found in the database");
		}
		
		List<User> currentUsers = userRepo.findAll();
		for(User u: currentUsers){
				if(u.getUsername().equals(user.getUsername()) ){
					if(!(u.equals(existingUser))){
						throw new UserNameInUseException("UserName "+u.getUsername()+" is already in use!");
					}
							
				}
		}
		
		existingUser.setUsername(user.getUsername());
		existingUser.setPerson(user.getPerson());
		existingUser.setRole(user.getRole());
		existingUser.setPassword(user.getPassword());
		
		
		roleRepo.save(existingUser.getRole());
		personRepo.save(existingUser.getPerson());
		
		existingUser = userRepo.save(existingUser);
		
		if(!(user.equals(existingUser))){
			throw new ObjectMisMatchException("There was an Object-mismatch error while updating "+user.getUsername()+". Please check the database for irreguralities");
		}
		
		logger.info(existingUser.getUsername()+"was successfully updated");
		
		
		
		return existingUser;

	}
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void resetPassword(User user) throws Exception{
		User existingUser = userRepo.findById(user.getId());
		
		if(existingUser == null){
			logger.info("Cannot Update null user");
			throw new TargetNotFoundInDBException("User could not be found in the database");
		}
		String newpass = generateRandomPassword();
		
		existingUser.setPassword(sde.encode(newpass));
		
		try {
			update(existingUser);
			mailService.sendResetPasswordMail(existingUser, newpass);
		} catch (Exception e) {
			throw e;
		}
		
			
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void delete(User user){
		User existingUser = userRepo.findById(user.getId());

		if(existingUser == null){
			logger.debug("Cannot delete -> existingUser is null");
			throw new TargetNotFoundInDBException("User could not be found in the database");
		}
		logger.debug("Trying to delete: " +existingUser.getUsername());

		personRepo.delete(existingUser.getPerson());
		roleRepo.delete(existingUser.getRole());
		userRepo.delete(existingUser);
		
		logger.info("User "+existingUser.getUsername()+" was deleted");
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
