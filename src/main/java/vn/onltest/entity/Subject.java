package vn.onltest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Subjects")
@Getter
@Setter
@NoArgsConstructor
public class Subject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String courseName;

    @Column(columnDefinition = "SMALLINT default 1", nullable = false)
    private int status; // 1: active, 0: inactive

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    @JsonIgnore
    private List<Group> groupList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    @JsonIgnore
    private List<Question> questionList;
}