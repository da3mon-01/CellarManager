package hu.pte.schafferg.cellarManager.repo;


import hu.pte.schafferg.cellarManager.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repository for User objects.
 * @author Da3mon
 *
 */
public interface UserRepository extends MongoRepository<User, String> {
	/**
	 * Returns a User based on username
	 * @param username
	 * @return
	 */
	 User findByUsername(String username);
	 /**
	  * Returns a User based on id.
	  * @param id
	  * @return
	  */
	 User findById(String id);
	 
	

}
