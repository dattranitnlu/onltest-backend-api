package vn.onltest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    private String fullName;
    private String phone;
    private String address;

    @Column(columnDefinition = "SMALLINT default 1", nullable = false)
    private int status; // 1: active, 0: inactive

    @Column(columnDefinition = "DATE")
    private Date dateOfBirth;

    @Column(columnDefinition = "CHAR", length = 1)
    private String gender; // store m: male and f: female

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(columnDefinition = "SMALLINT default 0", nullable = false)
    private int isDeleted;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UserRoles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles = new HashSet<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String username, String password, String email, String fullName, String phone, String address, int status, Date dateOfBirth, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

}