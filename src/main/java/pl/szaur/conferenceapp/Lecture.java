package pl.szaur.conferenceapp;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
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
        if(users == null)
            this.users = new HashSet<>();

        this.users.add(user);
    }
}
