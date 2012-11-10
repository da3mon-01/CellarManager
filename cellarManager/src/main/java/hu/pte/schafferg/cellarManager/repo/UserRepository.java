package hu.pte.schafferg.cellarManager.repo;


import hu.pte.schafferg.cellarManager.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	
	 User findByUsername(String username);
	 
	 User findById(String id);
	 
	

}
