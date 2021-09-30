package vn.onltest.controller.user;

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
import vn.onltest.model.response.error.BaseErrorResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.model.response.success.PageInfo;
import vn.onltest.model.response.success.PagingResultResponse;
import vn.onltest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import vn.onltest.util.PathUtil;
import vn.onltest.util.SwaggerUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/users")
@PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
@AllArgsConstructor
@Api(tags = "User")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "Create a user", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = 401, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = 403, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = 404, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = 500, message = SwaggerUtil.STATUS_500_REASON)
    })
    @PostMapping("create")
    public AbstractResponse createUser(@Valid @RequestBody RegisterAdminRequest userRequest,
                                                   BindingResult bindingResult) throws CustomMethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new CustomMethodArgumentNotValidException(bindingResult);
        } else {
            User createdUser = userService.createUser(userRequest);
            return new BaseResultResponse<>(HttpStatus.OK.value(), createdUser);
        }
    }

    @ApiOperation(value = "Get list lecturers", response = PagingResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = 401, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = 403, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = 404, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = 500, message = SwaggerUtil.STATUS_500_REASON)
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

    @ApiOperation(value = "Get list students", response = PagingResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = 401, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = 403, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = 404, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = 500, message = SwaggerUtil.STATUS_500_REASON)
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

    @ApiOperation(value = "Get list students for form", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = 401, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = 403, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = 404, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = 500, message = SwaggerUtil.STATUS_500_REASON)
    })
    @GetMapping("students")
    public AbstractResponse getStudentsIsExistedForSelectOption() {
        List<UserListViewForForm> responseData = userService.getStudentsIsExistedForSelectOption();
        return new BaseResultResponse<>(HttpStatus.OK.value(), responseData);
    }

    @ApiOperation(value = "Get list lecturers for form", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = 401, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = 403, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = 404, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = 500, message = SwaggerUtil.STATUS_500_REASON)
    })
    @GetMapping("lecturers")
    public AbstractResponse getLecturersIsExistedForSelectOption() {
        List<UserListViewForForm> responseData = userService.getLecturersIsExistedForSelectOption();
        return new BaseResultResponse<>(HttpStatus.OK.value(), responseData);
    }

    @ApiOperation(value = "Delete a lecturer by id", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = 400, message = SwaggerUtil.STATUS_400_REASON),
            @ApiResponse(code = 401, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = 403, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = 404, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = 500, message = SwaggerUtil.STATUS_500_REASON)
    })
    @DeleteMapping("delete/{username}")
    public AbstractResponse deleteLecturerById(@PathVariable String username) {
        int recordAfterDeleted = userService.setIsDeletedForUser(1, username);
        if (recordAfterDeleted == 1) {
            return new BaseResultResponse<>(
                    HttpStatus.OK.value(),
                    "User has username = " + username + " was be deleted.");
        }

        return new BaseErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "User has username = " + username + " was not found for deleting. Nothing will be done!");
    }
}

