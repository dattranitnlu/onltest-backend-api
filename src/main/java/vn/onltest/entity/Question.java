package vn.onltest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Questions")
@Getter
@Setter
@NoArgsConstructor
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "text")
    private String question;

    private double mark;
    private boolean isShuffle;

    @ManyToOne
    private QuestionType questionType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.EAGER)
    private List<Option> optionList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    @JsonIgnore
    private List<AnswerSheet> answerSheetList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<TestingDetail> testingDetailList;
}
