package vn.onltest.model.request;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class RegisterAdminRequest extends AbstractUserRequest {

    public RegisterAdminRequest(String username, String password, String email, String fullName,
                                String phone, String address, Set<String> roles) {
        super(username, password, email, fullName, phone, address, roles);
//        roles = new HashSet<>();
//        roles.add("ROLE_ADMIN");
    }

    public RegisterAdminRequest(String username, String password) {
        super(username, password);
        this.roles = new HashSet<>();
        this.roles.add("ROLE_LECTURER");
    }

    public RegisterAdminRequest(@NotNull @Size(min = 6, max = 50) String username,
                                @NotNull @Size(min = 8, max = 128) String password,
                                Set<String> roles) {
        super(username, password, roles);
    }
}