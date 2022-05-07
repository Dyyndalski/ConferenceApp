package pl.szaur.conferenceapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/save")
    public boolean register(@RequestBody UserDTO userdto){
        userService.addUser(userdto);
        return true;
    }

    @PostMapping("/updateEmail")
    public boolean updateEmail(@RequestBody UserDTO userDTO){
        return userService.updateEmail(userDTO);
    }

}
