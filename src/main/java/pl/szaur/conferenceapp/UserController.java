package pl.szaur.conferenceapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class UserController {

    @Autowired
    UserService userService;

    //TODO ENDPOINTS
}
