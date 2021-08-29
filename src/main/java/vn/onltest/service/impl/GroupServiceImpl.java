package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.Group;
import vn.onltest.repository.GroupRepository;
import vn.onltest.service.GroupService;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public Page<Group> getGroupsIsExistedByLecturerIdWithPagination(String username, Pageable pageable) {
        return groupRepository.findByUserAndIsDeleted(username, 0, pageable);
    }

    @Override
    public Page<Group> getGroupsIsExistedByLecturerIdWithQueryAndPagination(String username, String query, Pageable pageable) {
        return groupRepository.findByNameLikeAndUserAndIsDeleted(query, username, 0, pageable);
    }
}
