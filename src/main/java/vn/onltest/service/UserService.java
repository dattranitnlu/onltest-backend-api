package vn.onltest.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.onltest.model.entity.constant.ERole;
import vn.onltest.model.entity.Role;
import vn.onltest.model.entity.User;
import vn.onltest.model.projection.UserInfoSummary;
import vn.onltest.model.projection.UserListView;
import vn.onltest.model.projection.UserListViewForForm;
import vn.onltest.model.request.AbstractUserRequest;

import java.util.Collection;
import java.util.List;

public interface UserService {

    /**
     * Create a user if not existed
     *
     * @param userRequest: some info for creating user
     * @return User
     */
    @Transactional(readOnly = true)
    User createUser(AbstractUserRequest userRequest);

    /**
     * find existed user by username
     *
     * @param username
     * @return User
     */
    @Transactional(readOnly = true)
    User findExistedUserByUsername(String username);

    /**
     * find actived user by username
     *
     * @param username
     * @return User
     */
    @Transactional(readOnly = true)
    User findActivatedUserByUsername(String username);

    /**
     * List users by email and status
     *
     * @param email
     * @param status
     * @return List<User>
     */
    List<User> findByEmailAndStatus(String email, int status);

    /**
     * Get user info
     *
     * @param username:  input authenticated user
     * @param roles:     list roles
     * @param status:    integer type
     * @param isDeleted: integer type
     * @return UserInfoSummary
     */
    @Transactional(readOnly = true)
    UserInfoSummary findUserInfoSummary(String username, Collection<Role> roles, int status, int isDeleted);

    /**
     * List roles to list enum
     *
     * @param roles: list roles by String type
     * @return Collection<Role>
     */
    @Transactional(readOnly = true)
    Collection<Role> getListRoles(List<ERole> roles);

    /**
     * List lecturers but not search (pagination)
     *
     * @param pageable: some info to pagination
     * @return Page<UserListView>
     */
    @Transactional(readOnly = true)
    Page<UserListView> getLecturersIsExistedWithPagination(Pageable pageable);

    /**
     * Search lecturers (pagination)
     *
     * @param query:    input to search lecturer
     * @param pageable: some info to pagination
     * @return Page<UserListView>
     */
    @Transactional(readOnly = true)
    Page<UserListView> getLecturersIsExistedWithQueryAndPagination(String query, Pageable pageable);

    /**
     * List students but not search (pagination)
     *
     * @param pageable: some info to pagination
     * @return Page<UserListView>
     */
    @Transactional(readOnly = true)
    Page<UserListView> getStudentsIsExistedWithPagination(Pageable pageable);

    /**
     * Search students (pagination)
     *
     * @param query:    input to search student
     * @param pageable: some info to pagination
     * @return Page<UserListView>
     */
    @Transactional(readOnly = true)
    Page<UserListView> getStudentsIsExistedWithQueryAndPagination(String query, Pageable pageable);

    /**
     * Setting isDeleted field of user in database
     *
     * @param username:  input authenticated user
     * @param isDeleted: equal than 0 (not deleted) or 1 (deleted)
     * @return int
     */
    int setIsDeletedForUser(int isDeleted, String username);

    /**
     * List all students for select option from frontend
     * <p>
     * - role is student
     * - isDeleted is equal than 0
     * - status is equal than 1
     *
     * @return List<UserListViewForForm>
     */
    @Transactional(readOnly = true)
    @Cacheable("studentListNotPagination")
    List<UserListViewForForm> getStudentsIsExistedForSelectOption();

    /**
     * List all lecturers for select option from frontend
     * <p>
     * - role is lecturer
     * - isDeleted is equal than 0
     * - status is equal than 1
     *
     * @return List<UserListViewForForm>
     */
    @Transactional(readOnly = true)
    @Cacheable("lecturerListNotPagination")
    List<UserListViewForForm> getLecturersIsExistedForSelectOption();

    /**
     * Getting info of authenticated user by role
     *
     * @param username: input authenticated user
     * @param nameURL:  name of login API's URL (such as: admin, lecturer, lecturer)
     * @return UserInfoSummary
     */
    @Transactional(readOnly = true)
    UserInfoSummary getUserInfoWithRole(String username, String nameURL);
}
