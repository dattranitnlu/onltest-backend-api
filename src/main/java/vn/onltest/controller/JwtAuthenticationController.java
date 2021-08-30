package vn.onltest.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
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
import vn.onltest.util.PathUtil;
import vn.onltest.util.SwaggerUtil;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
@Api(tags = "Authentication (Admin || Student || Lecturer)")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @ApiOperation(value = "Login user (admin || lecturer || student)", response = JwtTokenResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = 400, message = SwaggerUtil.STATUS_400_REASON),
            @ApiResponse(code = 401, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = 403, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = 404, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = 500, message = SwaggerUtil.STATUS_500_REASON)
    })
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
            listEnumRoles = Arrays.asList(ERole.ROLE_ADMIN, ERole.ROLE_LECTURER);
        }

        Collection<Role> roles = userService.getListRoles(listEnumRoles);

        return userService.findUserInfoSummary(username, roles, 1, 0);
    }

}
