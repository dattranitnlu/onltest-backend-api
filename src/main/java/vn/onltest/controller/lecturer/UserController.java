package vn.onltest.controller.lecturer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.onltest.entity.User;
import vn.onltest.exception.movie.CustomMethodArgumentNotValidException;
import vn.onltest.model.projection.UserListView;
import vn.onltest.model.projection.UserListViewForForm;
import vn.onltest.model.request.RegisterAdminRequest;
import vn.onltest.model.response.AbstractResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.model.response.success.PageInfo;
import vn.onltest.model.response.success.PagingResultResponse;
import vn.onltest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import vn.onltest.util.PathUtil;
import vn.onltest.util.ServerResponseUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/users")
@PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER') or hasRole('SUPER_ADMIN')")
@AllArgsConstructor
@Api(tags = "User")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "Lấy danh sách giáo viên", response = PagingResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
    })
    @GetMapping("list")
    public AbstractResponse getLecturersIsExisted(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                            @RequestParam(name = "size", required = false, defaultValue = "25") int size,
                                                                            @RequestParam(name = "query", required = false) String query) {
        Page<UserListView> resultPage;
        Pageable pageable = PageRequest.of(page, size, Sort.by("fullName").ascending());
        if (query == null) {
            resultPage = userService.getLecturersIsExistedWithPagination(pageable);
        } else {
            resultPage = userService.getLecturersIsExistedWithQueryAndPagination(query, pageable);
        }

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

    @ApiOperation(value = "Lấy danh sách sinh viên (học sinh)", response = PagingResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
    })
    @GetMapping("students/list")
    public AbstractResponse getStudentsIsExisted(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                @RequestParam(name = "size", required = false, defaultValue = "25") int size,
                                                @RequestParam(name = "query", required = false) String query) {
        Page<UserListView> resultPage;
        Pageable pageable = PageRequest.of(page, size, Sort.by("fullName").ascending());
        if (query == null) {
            resultPage = userService.getStudentsIsExistedWithPagination(pageable);
        } else {
            resultPage = userService.getStudentsIsExistedWithQueryAndPagination(query, pageable);
        }

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

    @ApiOperation(value = "Lấy danh sách sinh viên cho form (Không phân trang)", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
    })
    @GetMapping("students")
    public AbstractResponse getStudentsIsExistedForSelectOption() {
        List<UserListViewForForm> responseData = userService.getStudentsIsExistedForSelectOption();
        return new BaseResultResponse<>(HttpStatus.OK.value(), responseData);
    }

    @ApiOperation(value = "Lấy danh sách giáo viên cho form (Không phân trang)", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
    })
    @GetMapping("lecturers")
    public AbstractResponse getLecturersIsExistedForSelectOption() {
        List<UserListViewForForm> responseData = userService.getLecturersIsExistedForSelectOption();
        return new BaseResultResponse<>(HttpStatus.OK.value(), responseData);
    }

}

