package pl.szaur.conferenceapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szaur.conferenceapp.DTO.LectureDTO;
import pl.szaur.conferenceapp.DTO.UserDTO;
import pl.szaur.conferenceapp.Model.Lecture;
import pl.szaur.conferenceapp.Repository.LectureRepository;
import pl.szaur.conferenceapp.Repository.UserRepository;
import pl.szaur.conferenceapp.Model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            if (CheckExistsByLoginAndEmail(userDTO)) {
                if (checkThatUserIsOnChosenLecture(userDTO))
                    return "Jesteś już zapisany na tą prelekcje.";
                if (!checkUserTime(userDTO))
                    return "Jesteś zapisany na inną konferencje w tym samym czasie";
                addUserToLecture(userDTO);
                return "Pomyślnie zapisano na prelekcje.";

//additional function not to allow add more than 1 user by the same email
            }else if(CheckExistsByEmail(userDTO)){
                return "Istnieje już użytkownik z podanym mailem";

            }else if(CheckExistsByLogin(userDTO)) {
                return "Podany login jest już zajęty.";

            }else if(checkFreePlaces(userDTO)){

                saveUser(userDTO);
                sendPseudoEmail(userDTO);
                return "Dodano użytkownika i zapisano na wykład.";
            }else{
                return "Brak wolnych miejsc na wybraną prelekcje.";
            }
        }
        return "Nie ma takiego wykładu.";
    }

    private boolean CheckExistsByEmail(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.getEmail())){
            return true;
        }
        return false;
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

        return byId.get().getUsers().size() == 5 ? false : true;
    }

    public boolean updateEmail(UserDTO userDTO) {
        Optional<User> userByLogin = userRepository.findUserByLogin(userDTO.getLogin());
        if(userByLogin.isPresent()){
            userByLogin.get().setEmail(userDTO.getEmail());
            userRepository.save(userByLogin.get());
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

    public String cancelReservation(UserDTO userDTO) {

        Optional<User> user = userRepository.findUserByLogin(userDTO.getLogin());

        if(user.isPresent()) {
            List<Lecture> lectures = lectureRepository.findAllLecturesByUsers(user.get()).stream()
                    .filter(lecture -> lecture.getId() == userDTO.getConferenceIndex())
                    .collect(Collectors.toList());

            if(lectures.isEmpty()) {
                return "Uzytkownik nie ma rezerwacji na wybrana prelekcje";
            }else{
                lectures.forEach(lectureToDelete -> {
                    user.get().removeLecture(lectureToDelete);
                    userRepository.save(user.get());
                });
                return "Anulowano rezerwacje.";
            }
        }
        return "Nie ma takiego użytkownika.";
    }

    public void sendPseudoEmail(UserDTO userDTO) {

        try (FileWriter fileWriter = new FileWriter("powiadomienia.txt", true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
            String message = "Data:" + LocalDateTime.now() + "\n" + "email:" + userDTO.getEmail()
                    + "\n" + "Zostałeś pomyślnie zapisany na konferencje!" + "\n";
            printWriter.println(message);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(user -> {
            return UserDTO.builder()
                    .id(user.getId())
                    .login(user.getLogin())
                    .email(user.getEmail())
                    .build();
        }).collect(Collectors.toList());
    }

    public boolean checkUserTime(UserDTO userDTO){

        for(Lecture lecture : lectureRepository.findAllLecturesByUsers(getUser(userDTO))){
            LocalTime startTime = getLecture(userDTO).getStartTime();
            LocalTime endTime = getLecture(userDTO).getEndTime();

            LocalTime startTimeLecture = lecture.getStartTime();
            LocalTime endTimeLecture = lecture.getEndTime();

            if((startTime.isAfter(startTimeLecture) || endTime.isAfter(startTimeLecture))
                    && (startTime.isBefore(endTimeLecture) || endTime.isBefore(endTimeLecture))) {
                return false;
            }
        }
        return true;
    }
}
