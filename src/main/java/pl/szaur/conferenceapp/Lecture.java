package pl.szaur.conferenceapp;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToMany (mappedBy = "lectures")
    Set<User> users = new HashSet<>();

    public void addUser(User user){
        this.users.add(user);
        user.getLectures().add(this);
    }
}
