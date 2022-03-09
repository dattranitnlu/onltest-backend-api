package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import vn.onltest.model.entity.Group;
import vn.onltest.model.request.GroupModel;

public interface GroupService {

    @Transactional
    Group createGroup(GroupModel groupModel);

    @Transactional(readOnly = true)
    Page<Group> getGroupsIsExistedByLecturerIdWithPagination(String username, Pageable pageable);

    @Transactional(readOnly = true)
    Page<Group> getGroupsIsExistedByLecturerIdWithQueryAndPagination(String username, String query, Pageable pageable);
}
