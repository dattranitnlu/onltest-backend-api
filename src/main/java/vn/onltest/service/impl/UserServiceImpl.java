package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import vn.onltest.entity.constant.DeleteStatusConstant;
import vn.onltest.entity.constant.ERole;
import vn.onltest.entity.Role;
import vn.onltest.entity.User;
import vn.onltest.entity.constant.StatusConstant;
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
    private final MessageSource messages;

    @Override
    public User createUser(AbstractUserRequest userRequest) {
        User localUser = userRepository.findByEmail(userRequest.getEmail());
        if (localUser != null) {
            throw new ServiceException(String.format(messages.getMessage("user.create.error.mail-existed", null, null), localUser.getEmail()));
        } else {
            String salt = BCrypt.gensalt();
            localUser = new User(userRequest.getUsername(),
                    BCrypt.hashpw(userRequest.getPassword(), salt),
                    userRequest.getEmail(),
                    userRequest.getFullName(),
                    userRequest.getPhone(),
                    userRequest.getAddress(),
                    StatusConstant.ACTIVATION,
                    userRequest.getDateOfBirth(),
                    userRequest.getGender());
            Set<String> strRoles = userRequest.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException(messages.getMessage("role.get.error.not-found", null, null)));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "ROLE_ADMIN":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException(messages.getMessage("role.get.error.not-found", null, null)));
                            roles.add(adminRole);
                            break;
                        case "ROLE_LECTURER":
                            Role modRole = roleRepository.findByName(ERole.ROLE_LECTURER)
                                    .orElseThrow(() -> new RuntimeException(messages.getMessage("role.get.error.not-found", null, null)));
                            roles.add(modRole);
                            break;
                        case "ROLE_STUDENT":
                            Role cusRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                                    .orElseThrow(() -> new RuntimeException(messages.getMessage("role.get.error.not-found", null, null)));
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
        return userRepository.findByUsernameAndIsDeleted(username, DeleteStatusConstant.NOT_DELETED);
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
            throw new NotFoundException(String.format(messages.getMessage("user.get.error.not-found.username", null, null), username));
        }
    }

    @Override
    public Collection<Role> getListRoles(List<ERole> roles) {
        if(roles.isEmpty()) {
            throw new ServiceException(messages.getMessage("role.list.error.not-found", null, null));
        } else {
            return roleRepository.findRolesByNameIn(roles);
        }
    }

    @Override
    public Page<UserListView> getLecturersIsExistedWithPagination(Pageable pageable) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(ERole.ROLE_LECTURER));
        return userRepository.findByRolesInAndIsDeleted(roles, DeleteStatusConstant.NOT_DELETED, pageable);
    }

    @Override
    public Page<UserListView> getLecturersIsExistedWithQueryAndPagination(String query, Pageable pageable) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(ERole.ROLE_LECTURER));
        return userRepository.findByUsernameLikeOrFullNameLikeOrEmailLikeOrPhoneLikeAndIsDeletedAndRolesIn(query, query, query, query, DeleteStatusConstant.NOT_DELETED, roles, pageable);
    }

    @Override
    public Page<UserListView> getStudentsIsExistedWithPagination(Pageable pageable) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(ERole.ROLE_STUDENT));
        return userRepository.findByRolesInAndIsDeleted(roles, DeleteStatusConstant.NOT_DELETED, pageable);
    }

    @Override
    public Page<UserListView> getStudentsIsExistedWithQueryAndPagination(String query, Pageable pageable) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(ERole.ROLE_STUDENT));
        return userRepository.findByUsernameLikeOrFullNameLikeOrEmailLikeOrPhoneLikeAndIsDeletedAndRolesIn(query, query, query, query, DeleteStatusConstant.NOT_DELETED, roles, pageable);
    }

    @Override
    public int setIsDeletedForUser(int isDeleted, String username) {
        User localUser = userRepository.findByUsernameAndIsDeleted(username, DeleteStatusConstant.NOT_DELETED);
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

        return findUserInfoSummary(username, roles, StatusConstant.ACTIVATION, DeleteStatusConstant.NOT_DELETED);
    }

    /**
     * Get list all users for select option on frontend
     * @param role: student, lecturer or admin
     */
    private List<UserListViewForForm> getUsersForFormByRole(ERole role) {
        Collection<Role> roles = this.getListRoles(Collections.singletonList(role));
        Sort sort = Sort.by(Sort.Order.asc("fullName"));
        return userRepository.findAllByRolesInAndStatusAndIsDeleted(roles, StatusConstant.ACTIVATION, DeleteStatusConstant.NOT_DELETED, sort);
    }

}

