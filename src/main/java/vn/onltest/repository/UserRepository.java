package vn.onltest.repository;

import vn.onltest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);

    @Query(value = "SELECT u FROM Users u WHERE u.username = ?1 and u.status = ?2")
    Optional<User> findByUsernameAndStatus(String username, int status);

    @Query(value = "SELECT u FROM Users u WHERE u.email = ?1 and u.status = ?2")
    Optional<User> findByEmailAndStatus(String email, int status);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
