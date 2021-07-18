package vn.onltest.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.Test;
import vn.onltest.repository.TestRepository;
import vn.onltest.service.TestService;

@Service
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Page<Test> getTestsWithStatus(String status, Pageable pageable) {
        if(status.compareToIgnoreCase("not-started") == 0) {
            return testRepository.findNotStartedTestsWithQuery(pageable);
        } else if (status.compareToIgnoreCase("on-going") == 0) {
            return testRepository.findOnGoingTestsWithQuery(pageable);
        } else {
            return testRepository.findDoneTestsWithQuery(pageable);
        }
    }
}
