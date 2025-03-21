package web_hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web_hibernate.entity.User;
import web_hibernate.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserRepositoryService implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUserById(Long id) {
        Optional<User> deletingUser = getUserById(id);
        deletingUser.ifPresent(user -> userRepository.delete(user));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
