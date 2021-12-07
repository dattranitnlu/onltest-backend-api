package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.onltest.entity.Test;
import vn.onltest.model.projection.TestingDetailListView;

import java.util.List;

public interface TestService {

    Page<Test> getTestsWithQuery(String username, String query, Pageable pageable);

    List<String> getTestCodeOfATest(long testId);

    @Transactional
    Page<TestingDetailListView> listAllQuestionInExam(long testId, String username, Pageable pageable);
}
