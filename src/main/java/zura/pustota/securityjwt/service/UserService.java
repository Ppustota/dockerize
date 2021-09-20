package zura.pustota.securityjwt.service;

import zura.pustota.securityjwt.model.Role;
import zura.pustota.securityjwt.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
