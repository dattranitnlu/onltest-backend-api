package vn.onltest.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractUserRequest {
    @NotNull
    @Size(min = 3, max = 50)
    protected String username;

    @NotNull
    @Size(min = 8, max = 128)
    protected String password;

    @Email
    protected String email;

    protected String fullName;
    protected String phone;
    protected String address;
    protected String gender;
    protected Date dateOfBirth;

    protected Set<String> roles;
    protected int status = 1;

    public AbstractUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AbstractUserRequest(String username,
                               String password,
                               String email,
                               String fullName,
                               String phone,
                               String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    public AbstractUserRequest(String username,
                               String password,
                               String email,
                               String fullName,
                               String phone,
                               String address,
                               Set<String> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.roles = roles;
    }

    public AbstractUserRequest(@NotNull @Size(min = 6, max = 50) String username,
                               @NotNull @Size(min = 8, max = 128) String password,
                               Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
