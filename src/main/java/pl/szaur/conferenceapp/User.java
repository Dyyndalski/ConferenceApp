package pl.szaur.conferenceapp;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String email;
    @ManyToMany(cascade = CascadeType.ALL)
    Set<Lecture> lectures = new HashSet<>();
}
