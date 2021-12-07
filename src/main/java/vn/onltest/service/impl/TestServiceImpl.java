package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.Test;
import vn.onltest.repository.TestRepository;
import vn.onltest.repository.TestingDetailRepository;
import vn.onltest.service.TestService;

import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TestingDetailRepository testingDetailRepository;

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
}
