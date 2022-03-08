package vn.onltest.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "TestingDetails")
@Getter
@Setter
@NoArgsConstructor
public class TestingDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int testOrder;
    private String testCode;

    @ManyToOne
    private Test test;

    @ManyToOne
    private Question question;
}
