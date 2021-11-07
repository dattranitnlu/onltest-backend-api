package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.Group;
import vn.onltest.entity.Subject;
import vn.onltest.entity.User;
import vn.onltest.entity.constant.DeleteStatusConstant;
import vn.onltest.exception.ServiceException;
import vn.onltest.model.request.GroupModel;
import vn.onltest.repository.GroupRepository;
import vn.onltest.repository.SubjectRepository;
import vn.onltest.repository.UserRepository;
import vn.onltest.service.GroupService;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public Group createGroup(GroupModel groupModel) {
        String supervisor = groupModel.getSupervisor().trim();
        String groupName = groupModel.getName().trim();
        String courseName = groupModel.getCourseName().trim();

        User lecturer = userRepository.findByUsername(supervisor);
        Subject subject = subjectRepository.findByCourseName(courseName);
        boolean checkExistedGroup = groupRepository.existsByNameAndSubjectAndLecturerIsAndIsDeleted(groupName, subject, lecturer, DeleteStatusConstant.NOT_DELETED);
        if(checkExistedGroup) {
            throw new ServiceException("Group with name '" + groupModel.getName() + "' already exists. Nothing will be done!");
        }

        List<String> listStudentEmails = groupModel.getStudentList();
        List<User> listStudents = userRepository.findDistinctByEmailIn(listStudentEmails);

        Group createdGroup = new Group(groupName, subject, lecturer, listStudents);
        return groupRepository.save(createdGroup);
    }

    @Override
    public Page<Group> getGroupsIsExistedByLecturerIdWithPagination(String username, Pageable pageable) {
        return groupRepository.findByLecturerAndIsDeleted(username, DeleteStatusConstant.NOT_DELETED, pageable);
    }

    @Override
    public Page<Group> getGroupsIsExistedByLecturerIdWithQueryAndPagination(String username, String query, Pageable pageable) {
        return groupRepository.findByNameLikeAndLecturerAndIsDeleted(query, username, DeleteStatusConstant.NOT_DELETED, pageable);
    }
}
