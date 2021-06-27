package vn.onltest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Options")
@Getter
@Setter
@NoArgsConstructor
public class Option implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "text")
    private String optionContent;

    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"question", "mark", "questionType", "optionList", "testingDetailList", "isShuffle"})
    private Question question;
}
