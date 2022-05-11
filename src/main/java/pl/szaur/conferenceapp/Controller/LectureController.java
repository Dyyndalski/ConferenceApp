package pl.szaur.conferenceapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.szaur.conferenceapp.DTO.LectureDTO;
import pl.szaur.conferenceapp.DTO.StatisticLectureDTO;
import pl.szaur.conferenceapp.DTO.StatisticTopicDTO;
import pl.szaur.conferenceapp.Service.LectureService;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000")
public class LectureController {

    @Autowired
    LectureService lectureService;

    @GetMapping("/lecture-managment/conf-plan")
    public List<LectureDTO> getConferencePlan(){
        return lectureService.getConferencePlan();
    }

    @GetMapping("/lecture-managment/stat-lectures")
    public List<StatisticLectureDTO> getStatisticsEveryLecture(){
        return lectureService.getStatisticsEveryLecture();
    }

    @GetMapping("/lecture-managment/stat-topics")
    public List<StatisticTopicDTO> getStatisticsEveryTopic(){
        return lectureService.getStatisticsEveryTopic();
    }
}