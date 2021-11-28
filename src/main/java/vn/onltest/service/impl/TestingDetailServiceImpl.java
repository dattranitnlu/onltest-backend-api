package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.Test;
import vn.onltest.entity.constant.StatusConstant;
import vn.onltest.exception.movie.NotFoundException;
import vn.onltest.model.projection.TestingDetailListView;
import vn.onltest.repository.TestRepository;
import vn.onltest.repository.TestingDetailRepository;
import vn.onltest.service.TestingDetailService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TestingDetailServiceImpl implements TestingDetailService {
    private final TestingDetailRepository testingDetailRepository;
    private final TestRepository testRepository;

    @Override
    public Page<TestingDetailListView> listAllQuestionInExam(long testId, String testCode, Pageable pageable) {
        Optional<Test> localTest = testRepository.findByIdAndStatus(testId, StatusConstant.ACTIVATION);
        if (localTest.isPresent()) {
            Test local = localTest.get();
            Page<TestingDetailListView> result = testingDetailRepository.findAllByTestAndTestCode(local, testCode, pageable);

            return result;
        } else {
            throw new NotFoundException("Not found test");
        }

    }
}