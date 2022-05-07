package pl.szaur.conferenceapp;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String login;
    private String email;
    private Long conferenceIndex;
}
