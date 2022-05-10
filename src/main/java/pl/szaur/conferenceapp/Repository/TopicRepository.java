package pl.szaur.conferenceapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szaur.conferenceapp.Model.Lecture;

@Repository
public interface TopicRepository extends JpaRepository<Lecture, Long> {
}
