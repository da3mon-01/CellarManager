package hu.pte.schafferg.cellarManager.repo;



import hu.pte.schafferg.cellarManager.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

}
