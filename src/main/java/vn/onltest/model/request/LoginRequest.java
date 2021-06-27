package vn.onltest.model.request;

public class LoginRequest extends AbstractUserRequest {
    public LoginRequest(String username, String password) {
        super(username, password);
    }
}