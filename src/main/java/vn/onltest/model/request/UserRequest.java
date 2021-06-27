package vn.onltest.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class UserRequest {
    @NotNull
    @Size(min = 6, max = 50)
    private String username;

    @NotNull
    @Size(min = 8, max = 128)
    private String password;

    @NotNull
    @Email
    private String email;

    private String fullName;
    private String phone;
    private String address;
    private Date dateOfBirth;
    private String gender;

    private Set<String> roles;

    private int status = 1;

}