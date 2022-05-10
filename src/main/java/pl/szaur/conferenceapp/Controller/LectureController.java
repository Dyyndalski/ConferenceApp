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

    @PostMapping("/conferencePlan")
    public List<LectureDTO> getConferencePlan(){
        return lectureService.getConferencePlan();
    }

    @GetMapping("/statisticsLectures")
    public List<StatisticLectureDTO> getStatisticsEveryLecture(){
        return lectureService.getStatisticsEveryLecture();
    }

    @GetMapping("/statisticsTopics")
    public List<StatisticTopicDTO> getStatisticsEveryTopic(){
        return lectureService.getStatisticsEveryTopic();
    }
}