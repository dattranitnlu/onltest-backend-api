package vn.onltest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.onltest.model.entity.TestingResult;

public interface TestingResultRepository extends JpaRepository<TestingResult, Long> {

    @Query("select count(a.id) from TestingResults a where a.test.id = :testId and a.student.username = :username and a.status = :status")
    int countTestingResultByTestAndStudentAndStatus(@Param("testId") long testId, @Param("username") String username, @Param("status") int status);

    @Query("select a from TestingResults a where a.test.id = :testId and a.student.username = :username and a.status = :status")
    TestingResult findTestingResultByTestAndStudentAndStatus(@Param("testId") long testId, @Param("username") String username, @Param("status") int status);

    @Query("select a from TestingResults a where a.test.id = :testId and a.student.username = :username")
    TestingResult findByTestAndStudent(@Param("testId") long testId, @Param("username") String username);
}
