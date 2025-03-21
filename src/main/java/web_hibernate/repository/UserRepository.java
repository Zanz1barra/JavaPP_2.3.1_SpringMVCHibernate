package web_hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web_hibernate.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long id);
}
