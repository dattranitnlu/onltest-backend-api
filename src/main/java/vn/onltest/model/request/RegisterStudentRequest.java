package vn.onltest.model.request;

import java.util.HashSet;

public class RegisterStudentRequest extends AbstractUserRequest {

    public RegisterStudentRequest(String email, String password) {
        super(email, password);
        this.roles = new HashSet<>();
        this.roles.add("ROLE_STUDENT");
    }

    @Override
    public String toString() {
        return "RegisterClientRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", roles=" + roles +
                ", status=" + status +
                '}';
    }
}