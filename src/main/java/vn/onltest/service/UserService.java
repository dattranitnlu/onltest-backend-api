package vn.onltest.service;

import vn.onltest.entity.ERole;
import vn.onltest.entity.Role;
import vn.onltest.entity.User;
import vn.onltest.model.projection.UserInfoSummary;
import vn.onltest.model.request.AbstractUserRequest;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User createUser(AbstractUserRequest userRequest);

    User findByEmail(String email);

    List<User> findByEmailAndStatus(String email, int status);

    UserInfoSummary findUserInfoSummary(String username, Collection<Role> roles, int status, int isDeleted);

    Collection<Role> getListRoles(List<ERole> roles);
}
