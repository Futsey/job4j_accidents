package accidents.repository.security;

import accidents.model.Authority;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

    Authority findByAuthority(String authority);
}
