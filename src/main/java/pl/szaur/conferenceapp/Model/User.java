package pl.szaur.conferenceapp.Model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String login;
    private String email;

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "users_lectures",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id")
    )
    private Set<Lecture> lectures = new HashSet<Lecture>();

//    public void addLecture(Lecture lecture){
//        //TODO
//        if(lectures == null)
//            this.lectures = new HashSet<>();
//
//        this.lectures.add(lecture);
//    }

    public void removeLecture(Lecture lecture){
        this.lectures.remove(lecture);
        lecture.getUsers().remove(this);
    }

}
