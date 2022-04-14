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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.onltest.model.entity.Test;
import vn.onltest.model.projection.TestingDetailListView;
import vn.onltest.model.request.SaveAnswerRequest;
import vn.onltest.model.response.AbstractResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.model.response.success.PageInfo;
import vn.onltest.model.response.success.PagingResultResponse;
import vn.onltest.model.response.success.ResultMessageResponse;
import vn.onltest.service.TestService;
import vn.onltest.util.PathUtil;
import vn.onltest.util.ServerResponseUtil;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/students/tests")
@PreAuthorize("hasRole('STUDENT')")
@AllArgsConstructor
@Api(tags = "Test")
public class TestController {
    private final TestService testService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @ApiOperation(value = "Lấy danh sách bài kiểm tra", response = PagingResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
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
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
    })
    @GetMapping("{testId}")
    public AbstractResponse listContentTestByTestId(
            Principal principal,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1") int size,
            @PathVariable long testId
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("testOrder").ascending());
        Page<TestingDetailListView> resultPage = testService.listAllQuestionInExam(testId, principal.getName(), pageable);

        return new PagingResultResponse<>(
                HttpStatus.OK.value(),
                resultPage.getContent(),
                new PageInfo(page, size, resultPage.getTotalElements(), resultPage.getTotalPages())
        );
    }

    @GetMapping("testCode")
    public AbstractResponse listTestCodeByTestId(@RequestParam long testId) {
        List<String> result = testService.getTestCodeOfATest(testId);
        return new BaseResultResponse<>(HttpStatus.OK.value(), result);
    }

    @ApiOperation(value = "Lưu kết quả bài làm của người làm", response = PagingResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
    })
    @PostMapping("saveAnswer")
    public AbstractResponse saveAnswer(Principal principal,
                                       @RequestParam long testId,
                                       @RequestParam long questionId,
                                       @RequestParam long optionId) {
//        testService.saveAnswer(principal.getName(), testId, questionId, optionId);
        SaveAnswerRequest request = new SaveAnswerRequest(principal.getName(), testId, questionId, optionId);

        kafkaTemplate.send("answer", String.valueOf(request));
        return new ResultMessageResponse(
                HttpStatus.OK.value(), "Saved answer successfully");
    }
}
