package vn.onltest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.onltest.entity.Test;
import vn.onltest.entity.TestingDetail;
import vn.onltest.model.projection.TestingDetailListView;

public interface TestingDetailRepository extends JpaRepository<TestingDetail, Long> {

    Page<TestingDetailListView> findAllByTestAndTestCode(Test test, String testCode, Pageable pageable);
}
