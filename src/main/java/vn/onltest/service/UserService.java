package vn.onltest.service;

import vn.onltest.entity.User;
import vn.onltest.model.request.AbstractUserRequest;

import java.util.List;

public interface UserService {
    User createUser(AbstractUserRequest userRequest);
    User findByEmail(String email);
    List<User> findByEmailAndStatus(String email, int status);
}
