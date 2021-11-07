package vn.onltest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.onltest.entity.constant.ERole;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
