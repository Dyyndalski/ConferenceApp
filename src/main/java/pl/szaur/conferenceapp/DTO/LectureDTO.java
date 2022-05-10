package pl.szaur.conferenceapp.DTO;

import lombok.Builder;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalTime;

@Builder
@Data
public class LectureDTO {
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long topicPathIndex;
}
