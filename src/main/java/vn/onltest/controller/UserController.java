package vn.onltest.controller;

import vn.onltest.entity.User;
import vn.onltest.exception.movie.CustomMethodArgumentNotValidException;
import vn.onltest.model.request.RegisterAdminRequest;
import vn.onltest.model.response.success.AbstractResultResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}

