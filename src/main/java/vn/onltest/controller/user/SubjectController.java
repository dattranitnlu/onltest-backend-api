package vn.onltest.controller.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.onltest.entity.Subject;
import vn.onltest.model.response.success.AbstractResultResponse;
import vn.onltest.model.response.success.PageInfo;
import vn.onltest.model.response.success.PagingResultResponse;
import vn.onltest.service.SubjectService;

@RestController
@RequestMapping("api/v1/subjects")
@PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("list")
    public AbstractResultResponse<List<Subject>> getSubjectsIsExisted(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
																	  @RequestParam(name = "size", required = false, defaultValue = "25") int size,
																	  @RequestParam(name = "query", required = false) String query) {

		Page<Subject> resultPage;
		Pageable pageable = PageRequest.of(page, size, Sort.by("courseName").ascending());
		if (query == null) {
			resultPage = subjectService.getSubjectsIsExistedWithPagination(pageable);
		}
		else {
			resultPage = subjectService.getSubjectsIsExistedWithQueryAndPagination(query, pageable);
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
