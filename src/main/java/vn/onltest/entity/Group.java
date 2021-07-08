package vn.onltest.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "Groups")
@Getter
@Setter
@NoArgsConstructor
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String className;

    @Column(columnDefinition = "SMALLINT default 1", nullable = false)
    private int status; // 1: active, 0: inactive

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Subject subject;
}
