package pl.szaur.conferenceapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            if(userRepository.existsByLogin(userDTO.getLogin())){
                Optional<User> userByLogin = userRepository.findUserByLogin(userDTO.getLogin());
                User user = userByLogin.get();

                user.addLecture(lecture.get());
                lecture.get().addUser(user);

                userRepository.save(user);

            }else {
                User user = User.builder()
                        .login(userDTO.getLogin())
                        .email(userDTO.getEmail())
                        .build();

                user.addLecture(lecture.get());
                lecture.get().addUser(user);

                userRepository.save(user);

                return true;
            }
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

    public List<LectureDTO> getLecturesByLogin(String login) {
        Optional<User> userByLogin = userRepository.findUserByLogin(login);

        if(userByLogin.isPresent()){
            return lectureRepository.findAllByUsers(userByLogin.get()).stream().map(lecture -> {
                return LectureDTO.builder()
                        .id(lecture.getId())
                        .name(lecture.getName())
                        .startTime(lecture.getStartTime())
                        .endTime(lecture.getEndTime())
                        .build();
            }).collect(Collectors.toList());
        }
        return null;
    }
}
