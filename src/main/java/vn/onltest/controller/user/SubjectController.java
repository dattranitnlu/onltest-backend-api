package vn.onltest.controller.user;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
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
import vn.onltest.model.projection.SubjectListView;
import vn.onltest.model.response.AbstractResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.model.response.success.PageInfo;
import vn.onltest.model.response.success.PagingResultResponse;
import vn.onltest.service.SubjectService;
import vn.onltest.util.PathUtil;
import vn.onltest.util.SwaggerUtil;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/subjects")
@PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
@AllArgsConstructor
@Api(tags = "Subject")
public class SubjectController {
    private final SubjectService subjectService;

	@ApiOperation(value = "Lấy danh sách những môn học", response = PagingResultResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = SwaggerUtil.SUCCEED_CODE, message = SwaggerUtil.STATUS_200_MESSAGE),
			@ApiResponse(code = SwaggerUtil.BAD_REQUEST_CODE, message = SwaggerUtil.STATUS_400_REASON),
			@ApiResponse(code = SwaggerUtil.UNAUTHORIZED_CODE, message = SwaggerUtil.STATUS_401_REASON),
			@ApiResponse(code = SwaggerUtil.NOT_ALLOWED_CODE, message = SwaggerUtil.STATUS_403_REASON),
			@ApiResponse(code = SwaggerUtil.NOT_FOUND_DATA_CODE, message = SwaggerUtil.STATUS_404_REASON),
			@ApiResponse(code = SwaggerUtil.INTERNAL_SERVER_ERROR_CODE, message = SwaggerUtil.STATUS_500_REASON)
	})
	@GetMapping("list")
    public AbstractResponse getSubjectsIsExisted(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
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
				new PageInfo(page, size, resultPage.getTotalElements(), resultPage.getTotalPages())
		);
    }

	@ApiOperation(value = "Lấy danh sách những môn học (Không phân trang)", response = BaseResultResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code = SwaggerUtil.SUCCEED_CODE, message = SwaggerUtil.STATUS_200_MESSAGE),
			@ApiResponse(code = SwaggerUtil.BAD_REQUEST_CODE, message = SwaggerUtil.STATUS_400_REASON),
			@ApiResponse(code = SwaggerUtil.UNAUTHORIZED_CODE, message = SwaggerUtil.STATUS_401_REASON),
			@ApiResponse(code = SwaggerUtil.NOT_ALLOWED_CODE, message = SwaggerUtil.STATUS_403_REASON),
			@ApiResponse(code = SwaggerUtil.NOT_FOUND_DATA_CODE, message = SwaggerUtil.STATUS_404_REASON),
			@ApiResponse(code = SwaggerUtil.INTERNAL_SERVER_ERROR_CODE, message = SwaggerUtil.STATUS_500_REASON)
	})
	@GetMapping
	public AbstractResponse getSubjectsIsExistedNotPagination() {
		List<SubjectListView> responseData = subjectService.getSubjectsIsExistedNotPagination();
		return new BaseResultResponse<>(HttpStatus.OK.value(), responseData);
	}
}
