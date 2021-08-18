package vn.onltest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.onltest.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

	Page<Subject> findAllBy(Pageable pageable);

	Page<Subject> findByCourseNameLike(String courseName, Pageable pageable);
}
