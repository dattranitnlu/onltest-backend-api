package vn.onltest.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.Group;
import vn.onltest.entity.User;
import vn.onltest.repository.GroupRepository;
import vn.onltest.repository.UserRepository;
import vn.onltest.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<Group> getGroupsIsExistedByLecturerIdWithPagination(String username, Pageable pageable) {
//        User lecturer = userRepository.findByUsernameAndIsDeleted(username, 0);
        return groupRepository.findByUserAndIsDeleted(username, 0, pageable);
    }

    @Override
    public Page<Group> getGroupsIsExistedByLecturerIdWithQueryAndPagination(String username, String query, Pageable pageable) {
//        User lecturer = userRepository.findByUsernameAndIsDeleted(username, 0);
        return groupRepository.findByNameLikeAndUserAndIsDeleted(query, username, 0, pageable);
    }
}
