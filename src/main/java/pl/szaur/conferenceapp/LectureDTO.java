package pl.szaur.conferenceapp;

import lombok.Builder;
import lombok.Data;
import java.time.LocalTime;

@Builder
@Data
public class LectureDTO {
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
}
