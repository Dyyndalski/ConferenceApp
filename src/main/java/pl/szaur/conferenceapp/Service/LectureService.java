package pl.szaur.conferenceapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.szaur.conferenceapp.DTO.LectureDTO;
import pl.szaur.conferenceapp.DTO.StatisticLectureDTO;
import pl.szaur.conferenceapp.DTO.StatisticTopicDTO;
import pl.szaur.conferenceapp.Model.Lecture;
import pl.szaur.conferenceapp.Repository.LectureRepository;
import pl.szaur.conferenceapp.Repository.TopicRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LectureService {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    TopicRepository topicRepository;

    public List<LectureDTO> getConferencePlan() {
        List<Lecture> allLectures = lectureRepository.findAll();

        return allLectures.stream().map(lecture -> {
            return LectureDTO.builder()
                    .id(lecture.getId())
                    .name(lecture.getName())
                    .startTime(lecture.getStartTime())
                    .endTime(lecture.getEndTime())
                    .topicPathIndex(lecture.getTopic().getId())
                    .build();
        }).collect(Collectors.toList());
    }

    public List<StatisticLectureDTO> getStatisticsEveryLecture() {

        List<Lecture> allLectures = lectureRepository.findAll();

        List<Long> numberOfUsersInEveryLecture = allLectures.stream().map(lecture -> {
            return lecture.getUsers().stream().count();
        }).collect(Collectors.toList());

        Long numberOfUsers = numberOfUsersInEveryLecture.stream().reduce(0L, Long::sum);

        List<Float> percentOfUsers = numberOfUsersInEveryLecture.stream().map(x -> x.floatValue()).map(x -> {
            if(numberOfUsers != 0)
                return x = x/((float)numberOfUsers) * 100.0f;
            else
                return 0.0f;
        }).collect(Collectors.toList());

        return IntStream.range(0, allLectures.size()).mapToObj(i -> {
            return StatisticLectureDTO.builder()
                    .lectureId(allLectures.get(i).getId())
                    .lectureName(allLectures.get(i).getName())
                    .percentOfUsers(percentOfUsers.get(i))
                    .build();
        }).collect(Collectors.toList());
    }

    public List<StatisticTopicDTO> getStatisticsEveryTopic() {

        Map<Long, Long> statisticMap = new HashMap<>();

        lectureRepository.findAll().stream().forEach(element -> {
            if(statisticMap.containsKey(element.getTopic().getId())){
                statisticMap.put(element.getTopic().getId(), statisticMap.get(element.getTopic().getId()) + element.getUsers().stream().count());
            }else{
                statisticMap.put(element.getTopic().getId(), element.getUsers().stream().count());
                System.out.println(element.getUsers().stream().count());
            }
        });

        System.out.println(statisticMap);

        Long numberOfUsers = statisticMap.values().stream().reduce(0L, Long::sum);


        return statisticMap.entrySet().stream().map(Map.Entry::getKey).map(element -> {
            return StatisticTopicDTO.builder()
                    .topicID(element)
                    .topic(topicRepository.getById(element).getTopic().getTopicName())
                    .percentOfUsers(Float.valueOf(statisticMap.get(element))/Float.valueOf(numberOfUsers) * 100.0f)
                    .build();
        }).collect(Collectors.toList());

    }
}
