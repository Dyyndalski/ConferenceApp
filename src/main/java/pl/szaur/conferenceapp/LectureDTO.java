package pl.szaur.conferenceapp;

import lombok.Data;
import java.time.LocalTime;

@Data
public class LectureDTO {
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
}
