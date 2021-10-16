package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.onltest.entity.ERole;
import vn.onltest.entity.Role;
import vn.onltest.entity.User;
import vn.onltest.model.projection.UserInfoSummary;
import vn.onltest.model.projection.UserListView;
import vn.onltest.model.projection.UserListViewForForm;
import vn.onltest.model.request.AbstractUserRequest;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User createUser(AbstractUserRequest userRequest);

    User findExistedUserByUsername(String username);

    List<User> findByEmailAndStatus(String email, int status);

    UserInfoSummary findUserInfoSummary(String username, Collection<Role> roles, int status, int isDeleted);

    Collection<Role> getListRoles(List<ERole> roles);

    Page<UserListView> getLecturersIsExistedWithPagination(Pageable pageable);

    Page<UserListView> getLecturersIsExistedWithQueryAndPagination(String query, Pageable pageable);

    Page<UserListView> getStudentsIsExistedWithPagination(Pageable pageable);

    Page<UserListView> getStudentsIsExistedWithQueryAndPagination(String query, Pageable pageable);

    int setIsDeletedForUser(int isDeleted, String username);

    List<UserListViewForForm> getStudentsIsExistedForSelectOption();

    List<UserListViewForForm> getLecturersIsExistedForSelectOption();

    UserInfoSummary getUserInfoWithRole(String username, String nameURL);
}
