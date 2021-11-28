package vn.onltest.controller.student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.onltest.entity.Test;
import vn.onltest.model.projection.TestingDetailListView;
import vn.onltest.model.response.AbstractResponse;
import vn.onltest.model.response.success.PageInfo;
import vn.onltest.model.response.success.PagingResultResponse;
import vn.onltest.service.TestService;
import vn.onltest.service.TestingDetailService;
import vn.onltest.util.PathUtil;
import vn.onltest.util.SwaggerUtil;

import java.security.Principal;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/students/tests")
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor
@Api(tags = "Test")
public class TestController {
    private final TestService testService;
//    private final TestResultService testResultService;
    private final TestingDetailService testingDetailService;
//    private final AnswerSheetService answerSheetService;

    @ApiOperation(value = "Lấy danh sách bài kiểm tra", response = PagingResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = SwaggerUtil.SUCCEED_CODE, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = SwaggerUtil.BAD_REQUEST_CODE, message = SwaggerUtil.STATUS_400_REASON),
            @ApiResponse(code = SwaggerUtil.UNAUTHORIZED_CODE, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = SwaggerUtil.NOT_ALLOWED_CODE, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = SwaggerUtil.NOT_FOUND_DATA_CODE, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = SwaggerUtil.INTERNAL_SERVER_ERROR_CODE, message = SwaggerUtil.STATUS_500_REASON)
    })
    @GetMapping
    public AbstractResponse getTestsByStudentId(Principal principal,
                                                @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                @RequestParam(name = "size", required = false, defaultValue = "12") int size,
                                                @RequestParam(name = "query", required = false, defaultValue = "done") String query) {
        String username = principal.getName();
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<Test> resultPage = testService.getTestsWithQuery(username, query.trim(), pageable);

        return new PagingResultResponse<>(
                HttpStatus.OK.value(),
                resultPage.getContent(),
                new PageInfo(page, size, resultPage.getTotalElements(), resultPage.getTotalPages()
                )
        );

    }

    @ApiOperation(value = "Lấy nội dung một bài kiểm tra bằng ID", response = PagingResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = SwaggerUtil.SUCCEED_CODE, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = SwaggerUtil.BAD_REQUEST_CODE, message = SwaggerUtil.STATUS_400_REASON),
            @ApiResponse(code = SwaggerUtil.UNAUTHORIZED_CODE, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = SwaggerUtil.NOT_ALLOWED_CODE, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = SwaggerUtil.NOT_FOUND_DATA_CODE, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = SwaggerUtil.INTERNAL_SERVER_ERROR_CODE, message = SwaggerUtil.STATUS_500_REASON)
    })
    @GetMapping("{testId}")
    public AbstractResponse listContentTestByTestId(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                    @RequestParam(name = "size", required = false, defaultValue = "3") int size,
                                                    @RequestParam(name = "testCode", required = false) String testCode,
                                                    @PathVariable long testId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("testOrder").ascending());
        Page<TestingDetailListView> resultPage = testingDetailService.listAllQuestionInExam(testId, testCode, pageable);

        return new PagingResultResponse<>(
                HttpStatus.OK.value(),
                resultPage.getContent(),
                new PageInfo(page, size, resultPage.getTotalElements(), resultPage.getTotalPages())
        );
    }
}
