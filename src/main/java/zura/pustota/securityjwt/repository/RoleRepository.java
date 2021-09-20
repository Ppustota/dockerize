package zura.pustota.securityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zura.pustota.securityjwt.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
