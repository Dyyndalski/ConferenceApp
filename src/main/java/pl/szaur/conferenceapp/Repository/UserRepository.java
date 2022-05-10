package pl.szaur.conferenceapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szaur.conferenceapp.Model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);
    Boolean existsByLogin(String login);
    Boolean existsByLoginAndEmail(String login, String email);
}
