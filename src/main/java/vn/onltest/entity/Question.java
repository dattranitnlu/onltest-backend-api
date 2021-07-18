package vn.onltest.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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

    @Column(columnDefinition = "SMALLINT DEFAULT 1", nullable = false, insertable = false)
    private int status; // 1: active, 0: inactive

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(columnDefinition = "SMALLINT DEFAULT 0", nullable = false, insertable = false)
    private int isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionType questionType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Option> optionList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.LAZY)
    private List<AnswerSheet> answerSheetList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", fetch = FetchType.LAZY)
    private List<TestingDetail> testingDetailList;
}
