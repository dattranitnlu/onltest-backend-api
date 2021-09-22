package vn.onltest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.onltest.entity.Role;
import vn.onltest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.onltest.model.projection.UserInfoSummary;
import vn.onltest.model.projection.UserListView;
import vn.onltest.model.projection.UserListViewForForm;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndIsDeleted(String email, int isDeleted);

    User findByEmail(String email);

    User findByUsername(String username);

    Optional<User> findByUsernameAndStatusAndIsDeleted(String username, int status, int isDeleted);

    Optional<User> findByEmailAndStatus(String email, int status);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Collection<UserInfoSummary> findByUsernameAndRolesInAndStatusAndIsDeleted(String username,
                                                                              Collection<Role> roles,
                                                                              int status,
                                                                              int isDeleted);

    Page<User> findByIsDeleted(int isDeleted, Pageable pageable);

    Page<UserListView> findByUsernameLikeOrFullNameLikeOrEmailLikeOrPhoneLikeAndIsDeleted(String username,
                                                                                          String fullName,
                                                                                          String email,
                                                                                          String phone,
                                                                                          int isDeleted,
                                                                                          Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Users u set u.isDeleted = ?1 where u.username = ?2")
    int setIsDeletedFor(int isDeleted, String username);

    Page<UserListView> findByRolesInAndIsDeleted(Collection<Role> roles, int isDeleted, Pageable pageable);

    Page<UserListView> findByUsernameLikeOrFullNameLikeOrEmailLikeOrPhoneLikeAndIsDeletedAndRolesIn(String username,
                                                                                                    String fullName,
                                                                                                    String email,
                                                                                                    String phone,
                                                                                                    int isDeleted,
                                                                                                    Collection<Role> roles,
                                                                                                    Pageable pageable);

    List<User> findDistinctByEmailIn(List<String> email);

    List<UserListViewForForm> findAllByRolesInAndStatusAndIsDeleted(Collection<Role> roles, int status, int isDeleted, Sort sort);
}
