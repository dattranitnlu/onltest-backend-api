package vn.onltest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.onltest.entity.constant.EQuestionType;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "QuestionTypes")
@Getter
@Setter
@NoArgsConstructor
public class QuestionType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private EQuestionType typeName;
}


