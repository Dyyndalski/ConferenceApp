package pl.szaur.conferenceapp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    Boolean existsUserByLogin(String login);
}
