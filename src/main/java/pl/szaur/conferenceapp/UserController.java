package pl.szaur.conferenceapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/save")
    public String register(@RequestBody UserDTO userdto){
        return userService.addUser(userdto);
    }

    @PostMapping("/updateEmail")
    public boolean updateEmail(@RequestBody UserDTO userDTO){
        return userService.updateEmail(userDTO);
    }

    @GetMapping("/getLecturesByLogin/{login}")
    public List<LectureDTO> getLecturesByLogin(@PathVariable String login){
        return userService.getLecturesByLogin(login);
    }

    @PostMapping("/cancel")
    public String cancel(@RequestBody UserDTO userdto){
        return userService.cancelReservation(userdto);
    }


}
