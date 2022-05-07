package pl.szaur.conferenceapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class LectureController {

    @Autowired
    LectureService lectureService;

    @PostMapping("/conferencePlan")
    public List<LectureDTO> getConferencePlan(@RequestBody UserDTO userDTO){
        return lectureService.getConferencePlan();
    }
}
