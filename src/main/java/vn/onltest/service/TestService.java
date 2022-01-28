package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.onltest.entity.Test;
import vn.onltest.model.projection.TestingDetailListView;
import vn.onltest.model.request.TestModelRequest;

import java.util.List;

public interface TestService {

    /**
     * Query tests (pagination)
     *
     * @param username: input to username
     * @param query:    input to search
     * @param pageable: some info to pagination
     * @return Page<Test>
     */
    @Transactional(readOnly = true)
    Page<Test> getTestsWithQuery(String username, String query, Pageable pageable);

    /**
     * Get test code by test id
     *
     * @param testId: input to testId
     * @return List<String>
     */
    @Transactional(readOnly = true)
    List<String> getTestCodeOfATest(long testId);

    /**
     * List all questions of a exam for student
     *
     * @param testId:   input to testId
     * @param username: input to username student
     * @param pageable: some info to pagination
     * @return Page<TestingDetailListView>
     */
    @Transactional
    Page<TestingDetailListView> listAllQuestionInExam(long testId, String username, Pageable pageable);

    /**
     * Create a test
     *
     * @param testModelRequest: input some fields for creating test
     * @return Test
     */
    @Transactional
    Test createTest(TestModelRequest testModelRequest);

}
