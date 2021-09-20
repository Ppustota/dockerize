package zura.pustota.securityjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import zura.pustota.securityjwt.model.Role;
import zura.pustota.securityjwt.model.User;
import zura.pustota.securityjwt.service.UserService;
import zura.pustota.securityjwt.service.UserServiceImpl;

import java.util.ArrayList;

@SpringBootApplication
public class SecurityjwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityjwtApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserServiceImpl service){
        return args -> {
          service.saveRole(new Role(1L, "ROLE_USER"));
          service.saveRole(new Role(2L, "ROLE_ADMIN"));
          service.saveUser(new User(1L,"john Doe", "john", "1234", new ArrayList<>()));
          service.saveUser(new User(2L,"Arnold", "arnold", "1234", new ArrayList<>()));
          service.addRoleToUser("john", "ROLE_USER");
          service.addRoleToUser("arnold", "ROLE_USER");
          service.addRoleToUser("arnold", "ROLE_ADMIN");
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
