package vn.onltest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.onltest.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("select o from Options o where o.question.id = :questionId")
    Option findByQuestion(@Param("questionId") long questionId);
}
