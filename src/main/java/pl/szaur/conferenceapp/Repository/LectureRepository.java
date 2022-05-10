package pl.szaur.conferenceapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szaur.conferenceapp.Model.Lecture;
import pl.szaur.conferenceapp.Model.User;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAllLecturesByUsers(User user);
}
