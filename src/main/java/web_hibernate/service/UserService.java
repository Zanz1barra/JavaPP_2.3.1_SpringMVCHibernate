package web_hibernate.service;

import web_hibernate.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    List<User> getUsersList();
    void deleteUserById(Long id);
    User addUser(User user);
    User updateUser(User user);
}
