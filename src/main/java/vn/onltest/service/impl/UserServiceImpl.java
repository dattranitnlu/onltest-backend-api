package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.onltest.entity.ERole;
import vn.onltest.entity.Role;
import vn.onltest.entity.User;
import vn.onltest.exception.ServiceException;
import vn.onltest.exception.movie.NotFoundException;
import vn.onltest.model.projection.UserInfoSummary;
import vn.onltest.model.projection.UserListView;
import vn.onltest.model.projection.UserListViewForForm;
import vn.onltest.model.request.AbstractUserRequest;
import vn.onltest.repository.RoleRepository;
import vn.onltest.repository.UserRepository;
import vn.onltest.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public User createUser(AbstractUserRequest userRequest) {
        User localUser = userRepository.findByEmail(userRequest.getEmail());
        if (localUser != null) {
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
    public User findExistedUserByUsername(String username) {
        return userRepository.findByUsernameAndIsDeleted(username, 0);
    }

    @Override
    public List<User> findByEmailAndStatus(String email, int status) {
        return userRepository.findByEmailAndStatus(email, status)
                .map(Collections::singletonList)
                .orElseGet(Collections::emptyList);
    }

    @Override
    public UserInfoSummary findUserInfoSummary(String username,
                                               Collection<Role> roles,
                                               int status,
                                               int isDeleted) {
        Collection<UserInfoSummary> userInfoSummaries = userRepository.findByUsernameAndRolesInAndStatusAndIsDeleted(
                username,
                roles,
                status,
                isDeleted
        );
        if(userInfoSummaries.size() == 1) {
            Iterator<UserInfoSummary> iterable = userInfoSummaries.stream().iterator();
            return iterable.next();
        } else {
            throw new NotFoundException("Not found username " + username);
        }
    }

    @Override
    public Collection<Role> getListRoles(List<ERole> roles) {
        if(roles.isEmpty()) {
            throw new ServiceException("List role need to be got equal is 0");
        } else {
            return roleRepository.findRolesByNameIn(roles);
        }
    }

    @Override
    public Page<UserListView> getLecturersIsExistedWithPagination(Pageable pageable) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(ERole.ROLE_LECTURER));
        return userRepository.findByRolesInAndIsDeleted(roles, 0, pageable);
    }

    @Override
    public Page<UserListView> getLecturersIsExistedWithQueryAndPagination(String query, Pageable pageable) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(ERole.ROLE_LECTURER));
        return userRepository.findByUsernameLikeOrFullNameLikeOrEmailLikeOrPhoneLikeAndIsDeletedAndRolesIn(query, query, query, query, 0, roles, pageable);
    }

    @Override
    public Page<UserListView> getStudentsIsExistedWithPagination(Pageable pageable) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(ERole.ROLE_STUDENT));
        return userRepository.findByRolesInAndIsDeleted(roles, 0, pageable);
    }

    @Override
    public Page<UserListView> getStudentsIsExistedWithQueryAndPagination(String query, Pageable pageable) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(ERole.ROLE_STUDENT));
        return userRepository.findByUsernameLikeOrFullNameLikeOrEmailLikeOrPhoneLikeAndIsDeletedAndRolesIn(query, query, query, query, 0, roles, pageable);
    }

    @Override
    public int setIsDeletedForUser(int isDeleted, String username) {
        User localUser = userRepository.findByUsernameAndIsDeleted(username, 0);
        if(localUser != null) {
            return userRepository.setIsDeletedFor(isDeleted, username);
        }
        return -1;
    }

    @Override
    public List<UserListViewForForm> getStudentsIsExistedForSelectOption() {
        return getUsersForFormByRole(ERole.ROLE_STUDENT);
    }

    @Override
    public List<UserListViewForForm> getLecturersIsExistedForSelectOption() {
        return getUsersForFormByRole(ERole.ROLE_LECTURER);
    }

    @Override
    public UserInfoSummary getUserInfoWithRole(String username, String nameURL) {
        List<ERole> listEnumRoles = null;
        if (nameURL.compareToIgnoreCase("student") == 0) {
            listEnumRoles = Collections.singletonList(ERole.ROLE_STUDENT);
        } else if (nameURL.compareToIgnoreCase("admin") == 0) {
            listEnumRoles = Collections.singletonList(ERole.ROLE_ADMIN);
        } else if (nameURL.compareToIgnoreCase("lecturer") == 0) {
            listEnumRoles = Collections.singletonList(ERole.ROLE_LECTURER);
        }

        Collection<Role> roles = getListRoles(listEnumRoles);

        return findUserInfoSummary(username, roles, 1, 0);
    }

    /**
     * Get list all users for select option on frontend
     * @param role: student, lecturer or admin
     */
    private List<UserListViewForForm> getUsersForFormByRole(ERole role) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(role));
        Sort sort = Sort.by(Sort.Order.asc("fullName"));
        return userRepository.findAllByRolesInAndStatusAndIsDeleted(roles, 1, 0, sort);
    }

}

