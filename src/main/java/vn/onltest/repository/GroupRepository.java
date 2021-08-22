package vn.onltest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.onltest.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Groups g where g.user.username = ?1 and g.isDeleted = ?2")
    Page<Group> findByUserAndIsDeleted(String username, int isDeleted, Pageable pageable);

    @Query("select g from Groups g where g.name like ?1 and g.user.username = ?2 and g.isDeleted = ?3")
    Page<Group> findByNameLikeAndUserAndIsDeleted(String className, String username, int isDeleted, Pageable pageable);
}