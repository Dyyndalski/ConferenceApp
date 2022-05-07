package pl.szaur.conferenceapp;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LectureRepository lectureRepository;

    public boolean addUser(UserDTO userDTO){
        Long lectureIndex = userDTO.getConferenceIndex();
        User user = populateUserEntity(userDTO);

        Lecture lecture = lectureRepository.getById(lectureIndex);
        lecture.addUser(user);
        userRepository.save(user);

        return true;
    }

    public boolean updateEmail(UserDTO userDTO) {
        if(userRepository.existsUserByLogin(userDTO.getLogin())) {
            User user = userRepository.findByLogin(userDTO.getLogin());
            user.setEmail(userDTO.getEmail());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User populateUserEntity(UserDTO userDTO){
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
