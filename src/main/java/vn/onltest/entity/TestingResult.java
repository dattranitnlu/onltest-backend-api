package vn.onltest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "TestingResults")
@Getter
@Setter
@NoArgsConstructor
public class TestingResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double grade;
    private boolean status;

    @ManyToOne
    private User student;

    @ManyToOne
    private Test test;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testingResult", fetch = FetchType.EAGER)
    private List<AnswerSheet> answerSheetList;
}
