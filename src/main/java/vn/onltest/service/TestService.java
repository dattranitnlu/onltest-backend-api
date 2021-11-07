package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.onltest.entity.Test;

public interface TestService {

    Page<Test> getTestsWithQuery(String username, String query, Pageable pageable);
}
