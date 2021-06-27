package vn.onltest.service.impl;

import vn.onltest.entity.ERole;
import vn.onltest.entity.Role;
import vn.onltest.entity.User;
import vn.onltest.exception.ServiceException;
import vn.onltest.model.request.AbstractUserRequest;
import vn.onltest.repository.RoleRepository;
import vn.onltest.repository.UserRepository;
import vn.onltest.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createUser(AbstractUserRequest userRequest) {
        User localUser = userRepository.findByEmail(userRequest.getEmail());
        if(localUser != null) {
            throw new ServiceException("Email " + localUser.getEmail() + " already exists. Nothing will be done!");
        } else {
            String salt = BCrypt.gensalt();
            localUser = new User(userRequest.getUsername(),
                    BCrypt.hashpw(userRequest.getPassword(), salt),
                    userRequest.getEmail(),
                    userRequest.getFullName(),
                    userRequest.getPhone(),
                    userRequest.getAddress(),
                    1,
                    userRequest.getDateOfBirth(),
                    userRequest.getGender());
            Set<String> strRoles = userRequest.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "ROLE_ADMIN":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                            break;
                        case "ROLE_LECTURER":
                            Role modRole = roleRepository.findByName(ERole.ROLE_LECTURER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);
                            break;
                        case "ROLE_STUDENT":
                            Role cusRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(cusRole);
                            break;
                    }
                });
            }

            localUser.setRoles(roles);

        }
        return userRepository.save(localUser);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByEmailAndStatus(String email, int status) {
        return userRepository.findByEmailAndStatus(email, status)
                .map(Collections::singletonList)
                .orElseGet(Collections::emptyList);
    }
}
