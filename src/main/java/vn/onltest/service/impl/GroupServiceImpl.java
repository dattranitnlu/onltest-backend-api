package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.onltest.entity.Group;
import vn.onltest.entity.Subject;
import vn.onltest.entity.User;
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
        String username = groupModel.getUsername().trim();
        String groupName = groupModel.getName().trim();
        String courseName = groupModel.getCourseName().trim();

        User lecturer = userRepository.findByUsername(username);
        Subject subject = subjectRepository.findByCourseName(courseName);
        boolean checkExistedGroup = groupRepository.existsByNameAndSubjectAndUserIsAndIsDeleted(groupName, subject, lecturer, 0);
        if(checkExistedGroup) {
            throw new ServiceException("Group with name \"" + groupModel.getName() + "\" already exists. Nothing will be done!");
        }

        List<String> listStudentEmails = groupModel.getStudentList();
        List<User> listStudents = userRepository.findDistinctByEmailIn(listStudentEmails);

        Group createdGroup = new Group(groupName, subject, lecturer, listStudents);
        return groupRepository.save(createdGroup);
    }

    @Override
    public Page<Group> getGroupsIsExistedByLecturerIdWithPagination(String username, Pageable pageable) {
        return groupRepository.findByUserAndIsDeleted(username, 0, pageable);
    }

    @Override
    public Page<Group> getGroupsIsExistedByLecturerIdWithQueryAndPagination(String username, String query, Pageable pageable) {
        return groupRepository.findByNameLikeAndUserAndIsDeleted(query, username, 0, pageable);
    }
}
