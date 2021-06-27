package vn.onltest.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserModel {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private int status; // 1: active, 0: inactive
    private Date dateOfBirth;
    private String gender; // store m: male and f: female
}
