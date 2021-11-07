package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.Test;
import vn.onltest.repository.TestRepository;
import vn.onltest.service.TestService;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;

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
}
