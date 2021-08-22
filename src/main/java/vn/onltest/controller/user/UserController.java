package vn.onltest.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.onltest.entity.User;
import vn.onltest.exception.movie.CustomMethodArgumentNotValidException;
import vn.onltest.model.projection.UserListView;
import vn.onltest.model.request.RegisterAdminRequest;
import vn.onltest.model.response.AbstractResponse;
import vn.onltest.model.response.error.BaseErrorResponse;
import vn.onltest.model.response.success.AbstractResultResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.model.response.success.PageInfo;
import vn.onltest.model.response.success.PagingResultResponse;
import vn.onltest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public AbstractResultResponse<User> createUser(@Valid @RequestBody RegisterAdminRequest userRequest,
                                                   BindingResult bindingResult) throws CustomMethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new CustomMethodArgumentNotValidException(bindingResult);
        } else {
            User createdUser = userService.createUser(userRequest);
            return new BaseResultResponse<>(HttpStatus.OK.value(), createdUser);
        }
    }

    @GetMapping("list")
    public AbstractResultResponse<List<UserListView>> getLecturersIsExisted(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
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

