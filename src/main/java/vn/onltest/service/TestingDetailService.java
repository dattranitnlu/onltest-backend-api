package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.onltest.model.projection.TestingDetailListView;

public interface TestingDetailService {
    @Transactional
    Page<TestingDetailListView> listAllQuestionInExam(long testId, String testCode, Pageable pageable);
}
