package pl.szaur.conferenceapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LectureRepository lectureRepository;

    public boolean addUser(UserDTO userDTO){
        Long lectureIndex = userDTO.getConferenceIndex();
        Optional<Lecture> lecture= lectureRepository.findById(lectureIndex);

        if(lecture.isPresent()) {
            User user = User.builder()
                    .login(userDTO.getLogin())
                    .email(userDTO.getEmail())
                    .build();

            user.addLecture(lecture.get());
            lecture.get().addUser(user);

            userRepository.save(user);

            return true;
        }
        return false;
    }

    public boolean updateEmail(UserDTO userDTO) {
        if(userRepository.findUserByLogin(userDTO.getLogin()).isPresent()){
            User user = User.builder()
                    .email(userDTO.getEmail())
                    .build();
            userRepository.save(user);

            return true;
        }
        return false;
    }
}
