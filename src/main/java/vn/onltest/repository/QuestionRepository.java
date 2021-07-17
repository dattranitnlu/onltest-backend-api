package vn.onltest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.onltest.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
