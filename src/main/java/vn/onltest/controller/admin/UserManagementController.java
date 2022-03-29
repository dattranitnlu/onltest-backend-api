package vn.onltest.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.onltest.model.entity.User;
import vn.onltest.exception.movie.CustomMethodArgumentNotValidException;
import vn.onltest.model.request.RegisterAdminRequest;
import vn.onltest.model.response.AbstractResponse;
import vn.onltest.model.response.error.BaseErrorResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.service.UserService;
import vn.onltest.util.PathUtil;
import vn.onltest.util.ServerResponseUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/admin/users")
@PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
@AllArgsConstructor
@Api(tags = "User Permission Management")
public class UserManagementController {
    private final UserService userService;

    @ApiOperation(value = "Tìm kiếm một người dùng bằng username", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
    })
    @GetMapping("get/{username}")
    public AbstractResponse getUserByUsername(@PathVariable String username) {
        User result = userService.findExistedUserByUsername(username);
        return new BaseResultResponse<>(HttpStatus.OK.value(), result);
    }

    @ApiOperation(value = "Tạo một tài khoản admin, giáo viên", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
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

    @ApiOperation(value = "Xóa một giáo viên bằng ID", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = ServerResponseUtil.SUCCEED_CODE, message = ServerResponseUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = ServerResponseUtil.BAD_REQUEST_CODE, message = ServerResponseUtil.STATUS_400_REASON),
            @ApiResponse(code = ServerResponseUtil.UNAUTHORIZED_CODE, message = ServerResponseUtil.STATUS_401_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_ALLOWED_CODE, message = ServerResponseUtil.STATUS_403_REASON),
            @ApiResponse(code = ServerResponseUtil.NOT_FOUND_DATA_CODE, message = ServerResponseUtil.STATUS_404_REASON),
            @ApiResponse(code = ServerResponseUtil.INTERNAL_SERVER_ERROR_CODE, message = ServerResponseUtil.STATUS_500_REASON)
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
