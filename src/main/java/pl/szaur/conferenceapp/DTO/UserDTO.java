package pl.szaur.conferenceapp.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private Long id;
    private String login;
    private String email;
    private Long conferenceIndex;
}
