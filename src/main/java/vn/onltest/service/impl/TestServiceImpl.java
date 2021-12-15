package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.Test;
import vn.onltest.entity.TestingResult;
import vn.onltest.entity.User;
import vn.onltest.entity.constant.StatusConstant;
import vn.onltest.exception.movie.NotFoundException;
import vn.onltest.model.projection.TestingDetailListView;
import vn.onltest.repository.TestRepository;
import vn.onltest.repository.TestingDetailRepository;
import vn.onltest.repository.TestingResultRepository;
import vn.onltest.service.TestService;
import vn.onltest.service.UserService;
import vn.onltest.util.RandomUtil;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TestingDetailRepository testingDetailRepository;
    private final TestingResultRepository testingResultRepository;
    private final UserService userService;
    private final MessageSource messages;

    @Override
    public Page<Test> getTestsWithQuery(String username, String query, Pageable pageable) {
        if(query.compareToIgnoreCase("not-started") == 0) {
            return testRepository.findNotStartedTestsWithQuery(pageable);
        } else if (query.compareToIgnoreCase("on-going") == 0) {
            return testRepository.findOnGoingTestsWithQuery(pageable);
        } else {
            return testRepository.findDoneTestsWithQuery(pageable);
        }
    }

    @Override
    public List<String> getTestCodeOfATest(long testId) {
        return testingDetailRepository.findDistinctByTest(testId);
    }

    @Override
    public Page<TestingDetailListView> listAllQuestionInExam(long testId, String username, Pageable pageable) {
        Optional<Test> localTest = testRepository.findByIdAndStatus(testId, StatusConstant.ACTIVATION);

        if (localTest.isPresent()) {
            username = username.trim();

            Test local = localTest.get();
            String randomTestCode = "";
//            int existedTest = testingResultRepository.countTestingResultByTestAndStudentAndStatus(testId, username, StatusConstant.ACTIVATION);
            TestingResult localTestingResult = testingResultRepository.findTestingResultByTestAndStudentAndStatus(testId, username, StatusConstant.ACTIVATION);

            if(localTestingResult == null) {
                TestingResult testingResult = new TestingResult();
                testingResult.setGrade(0);
                testingResult.setStatus(StatusConstant.ACTIVATION);

                // Random a test code for student
                List<String> listTestCode = getTestCodeOfATest(testId);
                randomTestCode = RandomUtil.randomTestCode(listTestCode);
                testingResult.setTestCode(randomTestCode);

                // Set this test
                testingResult.setTest(localTest.get());

                // Set student doing test with this test
                User student = userService.findActivatedUserByUsername(username);
                testingResult.setStudent(student);

                localTestingResult = testingResultRepository.save(testingResult);
            }

            Page<TestingDetailListView> result = testingDetailRepository.findAllByTestAndTestCode(local, localTestingResult.getTestCode(), pageable);

            return result;
        } else {
            throw new NotFoundException(String.format(messages.getMessage("test.get.error.not-found", null, null), testId));
        }

    }
}
