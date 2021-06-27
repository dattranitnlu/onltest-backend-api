package vn.onltest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "Tests")
@Getter
@Setter
@NoArgsConstructor
public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date startDate;
    private Date endDate;
    private Date time;
    private int code;
    private Date startTime;
    private boolean status;

    @ManyToOne
    private Subject subject;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private List<TestingResult> testingResultList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private List<TestingDetail> testingDetailList;
}