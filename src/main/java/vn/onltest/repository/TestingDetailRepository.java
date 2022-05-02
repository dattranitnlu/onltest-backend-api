package vn.onltest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import vn.onltest.model.entity.Test;
import vn.onltest.model.entity.TestingDetail;
import vn.onltest.model.projection.TestingDetailListView;

import java.util.List;

public interface TestingDetailRepository extends JpaRepository<TestingDetail, Long> {

    Page<TestingDetailListView> findAllByTestAndTestCode(Test test, String testCode, Pageable pageable);

    @Query("select distinct a.testCode from TestingDetails a where a.test.id = :testId")
    List<String> findDistinctByTest(@Param("testId") long testId);

    @Query("select count(t) from TestingDetails t where t.testCode = ?1 and t.test.id = ?2")
    int countTotalQuestionByTestCodeAndTestId(String testCode, long testId);
}
