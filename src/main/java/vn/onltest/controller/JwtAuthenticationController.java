package vn.onltest.controller;

import vn.onltest.config.jwt.JwtUtils;
import vn.onltest.entity.User;
import vn.onltest.model.request.JwtTokenRequest;
import vn.onltest.model.response.success.JwtTokenResponse;
import vn.onltest.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("login")
    public JwtTokenResponse<?> createAuthenticatedToken(@Valid @RequestBody JwtTokenRequest jwtTokenRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtTokenRequest.getUsername(), jwtTokenRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(jwtTokenRequest.getUsername());
        String token = jwtUtils.generateJwtToken(authentication);
        return new JwtTokenResponse<>(token, user);
    }
}
