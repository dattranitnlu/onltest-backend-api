package vn.onltest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.onltest.model.entity.Group;
import vn.onltest.model.entity.Subject;
import vn.onltest.model.entity.User;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Groups g where g.lecturer.username = ?1 and g.isDeleted = ?2")
    Page<Group> findByLecturerAndIsDeleted(String username, int isDeleted, Pageable pageable);

    @Query("select g from Groups g where g.name like ?1 and g.lecturer.username = ?2 and g.isDeleted = ?3")
    Page<Group> findByNameLikeAndLecturerAndIsDeleted(String className, String username, int isDeleted, Pageable pageable);

    List<Group> findByNameAndSubjectAndLecturerAndIsDeleted(String name, Subject subject, User user, int isDeleted);

    Boolean existsByNameAndSubjectAndLecturerIsAndIsDeleted(String name, Subject subject, User user, int isDeleted);
}
