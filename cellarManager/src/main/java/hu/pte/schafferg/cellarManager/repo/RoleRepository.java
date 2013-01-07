package hu.pte.schafferg.cellarManager.repo;



import hu.pte.schafferg.cellarManager.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * Repository for Role objects.
 * @author Da3mon
 *
 */
public interface RoleRepository extends MongoRepository<Role, String> {

}
