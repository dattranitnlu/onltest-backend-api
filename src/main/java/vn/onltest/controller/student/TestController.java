package vn.onltest.controller.student;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.onltest.entity.Test;
import vn.onltest.model.response.success.AbstractResultResponse;
import vn.onltest.model.response.success.PageInfo;
import vn.onltest.model.response.success.PagingResultResponse;
import vn.onltest.service.TestService;

import java.util.List;

@RestController
@RequestMapping("api/v1/students/{studentId}")
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("tests")
    public AbstractResultResponse<List<Test>> getTestsByStudentId(@PathVariable String studentId,
                                                                  @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                  @RequestParam(name = "size", required = false, defaultValue = "12") int size,
                                                                  @RequestParam(name = "query", required = false, defaultValue = "done") String query) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<Test> resultPage = testService.getTestsWithStatus(query.trim(), pageable);

        return new PagingResultResponse<>(
                HttpStatus.OK.value(),
                resultPage.getContent(),
                new PageInfo(
                        page,
                        size,
                        (int) resultPage.getTotalElements(),
                        resultPage.getTotalPages()
                )
        );

    }
}
