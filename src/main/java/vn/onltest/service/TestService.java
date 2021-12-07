package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.onltest.entity.Test;

import java.util.List;

public interface TestService {

    Page<Test> getTestsWithQuery(String username, String query, Pageable pageable);

    List<String> getTestCodeOfATest(long testId);
}
