package pl.szaur.conferenceapp.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StatisticLectureDTO {
    private Long lectureId;
    private String lectureName;
    private Float percentOfUsers;
}
