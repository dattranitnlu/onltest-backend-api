package vn.onltest.controller;

import vn.onltest.config.jwt.JwtUtils;
import vn.onltest.entity.ERole;
import vn.onltest.entity.Role;
import vn.onltest.model.projection.UserInfoSummary;
import vn.onltest.model.request.JwtTokenRequest;
import vn.onltest.model.response.success.JwtTokenResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.onltest.service.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("api/v1/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtUtils jwtUtils,
                                       UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("{name}")
    public JwtTokenResponse<?> createAuthenticatedToken(@PathVariable String name,
                                                        @Valid @RequestBody JwtTokenRequest jwtTokenRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtTokenRequest.getUsername(),
                        jwtTokenRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserInfoSummary user = getUserInfoWithRole(jwtTokenRequest.getUsername(), name);
        String token = jwtUtils.generateJwtToken(authentication);
        return new JwtTokenResponse<>(token, user);
    }

    private UserInfoSummary getUserInfoWithRole(String username, String nameURL) {
        List<ERole> listEnumRoles = null;
        if (nameURL.compareToIgnoreCase("student") == 0) {
            listEnumRoles = Collections.singletonList(ERole.ROLE_STUDENT);
        } else if (nameURL.compareToIgnoreCase("admin") == 0) {
            listEnumRoles = Arrays.asList(ERole.ROLE_ADMIN, ERole.ROLE_STUDENT);
        }

        Collection<Role> roles = userService.getListRoles(listEnumRoles);

        return userService.findUserInfoSummary(username, roles, 1, 0);
    }

}
