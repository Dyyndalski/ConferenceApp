package pl.szaur.conferenceapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LectureRepository lectureRepository;

    public String addUser(UserDTO userDTO) {

        if (CheckLectureExist(userDTO)) {
            if (CheckExistsByLoginAndEmail(userDTO)){
                if(checkThatUserIsOnChosenLecture(userDTO))
                    return "Jesteś już zapisany na tą prelekcje.";

                addUserToLecture(userDTO);
                return "Pomyślnie zapisano na prelekcje.";

            }else if(CheckExistsByLogin(userDTO)) {
                return "Podany login jest już zajęty.";

            }else if(checkFreePlaces(userDTO)){

                saveUser(userDTO);
                return "Dodano użytkownika i zapisano na wykład.";
            }else{
                return "Brak wolnych miejsc na wybraną prelekcje.";
            }
        }
        return "Nie ma takiego wykładu.";
    }

    private boolean CheckLectureExist(UserDTO userDTO) {
        if(lectureRepository.findById(userDTO.getConferenceIndex()).isPresent())
            return true;
        return false;
    }

    private void saveUser(UserDTO userDTO) {
        User user = User.builder()
                .login(userDTO.getLogin())
                .email(userDTO.getEmail())
                .build();

        Lecture lecture = getLecture(userDTO);

        if(user.getLectures() == null)
            user.setLectures(new HashSet<>());

        user.getLectures().add(lecture);
        lecture.getUsers().add(user);

        userRepository.save(user);
    }

    private boolean CheckExistsByLogin(UserDTO userDTO) {
        if(userRepository.existsByLogin(userDTO.getLogin())){
            return true;
        }
        return false;
    }

    private Lecture getLecture(UserDTO userDTO){
        return lectureRepository.findById(userDTO.getConferenceIndex()).get();
    }

    private User getUser(UserDTO userDTO){
        return userRepository.findUserByLogin(userDTO.getLogin()).get();
    }

    private boolean CheckExistsByLoginAndEmail(UserDTO userDTO){
        if(userRepository.existsByLoginAndEmail(userDTO.getLogin(), userDTO.getEmail()))
            return true;
        return false;
    }

    private void addUserToLecture(UserDTO userDTO){
        Lecture lecture = getLecture(userDTO);
        User user = getUser(userDTO);

        user.getLectures().add(lecture);
        lecture.getUsers().add(user);

        userRepository.save(user);
    }

    private boolean checkThatUserIsOnChosenLecture(UserDTO userDTO) {
        Optional<User> user = userRepository.findUserByLogin(userDTO.getLogin());
        return user.filter(value -> lectureRepository.findAllLecturesByUsers(value).stream()
                .filter(lecture -> lecture.getId() == userDTO.getConferenceIndex())
                .count() > 0).isPresent();
    }

    private Boolean checkFreePlaces(UserDTO userDTO) {
        Optional<Lecture> byId = lectureRepository.findById(userDTO.getConferenceIndex());

        return byId.get().getUsers().stream().count() == 5 ? false : true;
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
            return lectureRepository.findAllLecturesByUsers(userByLogin.get()).stream().map(lecture -> {
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
