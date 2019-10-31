package com.example.library.Repository;

import com.example.library.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);
    Optional<User> findUserByLogin(String login);
    User findUserByLoginAndPassword(String login, String password);

    List<User> findUsersByLoginAndPassword(String username, String encode);
}
