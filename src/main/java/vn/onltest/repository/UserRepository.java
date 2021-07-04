package vn.onltest.repository;

import vn.onltest.entity.Role;
import vn.onltest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.onltest.model.projection.UserInfoSummary;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

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
}
