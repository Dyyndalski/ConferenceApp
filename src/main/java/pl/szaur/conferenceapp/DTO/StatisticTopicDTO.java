package pl.szaur.conferenceapp.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StatisticTopicDTO {
    private Long topicID;
    private String topic;
    private Float percentOfUsers;
}
