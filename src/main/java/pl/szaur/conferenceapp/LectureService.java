package pl.szaur.conferenceapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureService {

    @Autowired
    LectureRepository lectureRepository;

    public List<LectureDTO> getConferencePlan() {
        List<Lecture> allLectures = lectureRepository.findAll();

        return allLectures.stream().map(lecture -> {
            return LectureDTO.builder()
                    .id(lecture.getId())
                    .name(lecture.getName())
                    .startTime(lecture.getStartTime())
                    .endTime(lecture.getEndTime())
                    .build();
        }).collect(Collectors.toList());
    }
}
