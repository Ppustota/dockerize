package zura.pustota.securityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zura.pustota.securityjwt.model.User;



public interface UserRepository extends JpaRepository<User, Long> {
        User findByUsername(String username);
}
