package vn.onltest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.onltest.controller.student.TestController;

@SpringBootTest
public class DemoSonarqubeTest {
    @Autowired
    private TestController testController;

    @Test
    public void test_getMessage() {
        String message = testController.getMessage();
        Assertions.assertNotNull(message);
    }
}
