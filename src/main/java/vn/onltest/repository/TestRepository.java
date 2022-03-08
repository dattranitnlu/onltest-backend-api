package vn.onltest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.onltest.model.entity.Test;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {

    @Query(value = "SELECT m FROM Tests m " +
            "WHERE m.startDate > CURRENT_TIMESTAMP AND m.status = 0")
    Page<Test> findNotStartedTestsWithQuery(Pageable pageable);

    @Query(value = "SELECT m FROM Tests m " +
            "WHERE m.startDate < CURRENT_TIMESTAMP AND m.endDate > CURRENT_TIMESTAMP AND m.status = 1")
    Page<Test> findOnGoingTestsWithQuery(Pageable pageable);

    @Query(value = "SELECT m FROM Tests m " +
            "WHERE m.endDate < CURRENT_TIMESTAMP AND m.status = 0")
    Page<Test> findDoneTestsWithQuery(Pageable pageable);

    Optional<Test> findByIdAndStatus(long id, int status);

}
