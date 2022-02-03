package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.*;
import vn.onltest.entity.constant.StatusConstant;
import vn.onltest.exception.ServiceException;
import vn.onltest.exception.movie.NotFoundException;
import vn.onltest.model.projection.TestingDetailListView;
import vn.onltest.model.request.TestModelRequest;
import vn.onltest.repository.*;
import vn.onltest.service.TestService;
import vn.onltest.service.UserService;
import vn.onltest.util.RandomUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TestingDetailRepository testingDetailRepository;
    private final TestingResultRepository testingResultRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final AnswersSheetRepository answersSheetRepository;
    private final UserService userService;
    private final MessageSource messages;

    @Override
    public Page<Test> getTestsWithQuery(String username, String query, Pageable pageable) {
        if (query.compareToIgnoreCase("not-started") == 0) {
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

            if (localTestingResult == null) {
                TestingResult testingResult = new TestingResult();
                testingResult.setGrade(0);
                testingResult.setStatus(StatusConstant.ACTIVATION);

                Calendar date = Calendar.getInstance();
                long timeInSecs = date.getTimeInMillis();
                Date timeAfterAdding = new Date(timeInSecs + (local.getDuration() * 60 * 1000));
                testingResult.setFinishTime(timeAfterAdding);

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

    @Override
    public Test createTest(TestModelRequest testModelRequest) {
        testModelRequest.setTitle(testModelRequest.getTitle().trim());
        testModelRequest.setDescription(testModelRequest.getDescription().trim());
        testModelRequest.setSubjectName(testModelRequest.getSubjectName().trim());

        Test createdTest = new Test();
        createdTest.setTitle(testModelRequest.getTitle());
        createdTest.setDescription(testModelRequest.getDescription());
        createdTest.setStartDate(testModelRequest.getStartDate());
        createdTest.setEndDate(testModelRequest.getEndDate());
        createdTest.setDuration(testModelRequest.getDuration());

        Subject subject = subjectRepository.findByCourseName(testModelRequest.getSubjectName());
        if (subject != null) {
            createdTest.setSubject(subject);
        } else {
            throw new NotFoundException(String.format(messages.getMessage("subject.get.error.not-found", null, null), testModelRequest.getSubjectName()));
        }

        return testRepository.save(createdTest);
    }

    @Override
    public void saveAnswer(String username, long testId, long questionId, long optionId) {
        TestingResult localTestingResult = testingResultRepository.findTestingResultByTestAndStudentAndStatus(testId, username, StatusConstant.ACTIVATION);
        Optional<Question> question = questionRepository.findById(questionId);
        Optional<Option> option = optionRepository.findById(optionId);

        if (localTestingResult != null && question.isPresent()) {
            AnswerSheet answerSheet = getExistedAnswerFromDatabase(localTestingResult.getId(), questionId);
            answerSheet.setTestingResult(localTestingResult);
            answerSheet.setQuestion(question.get());

            if (option.isPresent()) {
                answerSheet.setOptionId(optionId);

                if(option.get().isCorrect()) {
                    answerSheet.setGrade(question.get().getMark());
                } else {
                    answerSheet.setGrade(0.0);
                }
            } else {
                throw new NotFoundException(String.format(messages.getMessage("option.get.error.not-found", null, null), option.get().getId()));
            }
            Calendar c = Calendar.getInstance();
            Date currentDate = c.getTime();
            Date finishTime = localTestingResult.getFinishTime();

            if(currentDate.before(finishTime)) {
                answerSheet.setChosenTime(currentDate);
                answersSheetRepository.save(answerSheet);
            } else {
                throw new ServiceException(messages.getMessage("answer.get.error.timeout", null, null));
            }

        } else {
            throw new ServiceException(messages.getMessage("answer.get.error.save", null, null));
        }
    }

    /**
     *  Getting existed answer in database
     *
     *  if existed -> return
     *  else -> create a new
     *
     * @param testResultId: input to testId of student
     * @param questionId: input to question
     */
    private AnswerSheet getExistedAnswerFromDatabase(long testResultId, long questionId) {
        Optional<AnswerSheet> localAnswerSheet = answersSheetRepository.findByTestingResultAndAndQuestionAndOptionId(testResultId, questionId);
        return localAnswerSheet.isPresent() ? localAnswerSheet.get() : new AnswerSheet();
    }
}
