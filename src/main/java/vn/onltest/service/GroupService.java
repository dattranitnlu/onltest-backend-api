package vn.onltest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.onltest.entity.Group;
import vn.onltest.model.request.GroupModel;

public interface GroupService {

    Group createGroup(GroupModel groupModel);

    Page<Group> getGroupsIsExistedByLecturerIdWithPagination(String username, Pageable pageable);

    Page<Group> getGroupsIsExistedByLecturerIdWithQueryAndPagination(String username, String query, Pageable pageable);
}
