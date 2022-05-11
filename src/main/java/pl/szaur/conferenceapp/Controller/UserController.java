package pl.szaur.conferenceapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.szaur.conferenceapp.DTO.LectureDTO;
import pl.szaur.conferenceapp.DTO.UserDTO;
import pl.szaur.conferenceapp.Service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user-managment/user")
    public String register(@RequestBody UserDTO userdto){
        return userService.addUser(userdto);
    }

    @PatchMapping("/user-managment/email")
    public boolean updateEmail(@RequestBody UserDTO userDTO){
        return userService.updateEmail(userDTO);
    }

    @GetMapping("/user-managment/lectures/{login}")
    public List<LectureDTO> getLecturesByLogin(@PathVariable String login){
        return userService.getLecturesByLogin(login);
    }

    @PostMapping("/user-managment/reservation")
    public String cancel(@RequestBody UserDTO userdto){
        return userService.cancelReservation(userdto);
    }

    @GetMapping("/user-managment/users")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }
}
