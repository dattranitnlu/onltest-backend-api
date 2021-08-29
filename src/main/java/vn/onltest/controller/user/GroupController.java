package vn.onltest.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.onltest.entity.Group;
import vn.onltest.model.response.success.AbstractResultResponse;
import vn.onltest.model.response.success.PageInfo;
import vn.onltest.model.response.success.PagingResultResponse;
import vn.onltest.service.GroupService;

import java.util.List;

@RestController
@RequestMapping("api/v1/groups")
@PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
@AllArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("list/{lecturerUsername}")
    public AbstractResultResponse<List<Group>> getGroupsIsExistedByLecturerId(
            @PathVariable String lecturerUsername,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "25") int size,
            @RequestParam(name = "query", required = false) String query
    ) {
        Page<Group> resultPage;
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        if (query == null) {
            resultPage = groupService.getGroupsIsExistedByLecturerIdWithPagination(lecturerUsername, pageable);
        } else {
            resultPage = groupService.getGroupsIsExistedByLecturerIdWithQueryAndPagination(lecturerUsername, query, pageable);
        }

        return new PagingResultResponse<>(
                HttpStatus.OK.value(),
                resultPage.getContent(),
                new PageInfo(
                        page,
                        size,
                        (int) resultPage.getTotalElements(),
                        resultPage.getTotalPages()
                )
        );
    }
}
