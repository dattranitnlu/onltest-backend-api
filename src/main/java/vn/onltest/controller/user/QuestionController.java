package vn.onltest.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.onltest.entity.Question;
import vn.onltest.exception.movie.CustomMethodArgumentNotValidException;
import vn.onltest.model.response.AbstractResponse;
import vn.onltest.model.response.success.BaseResultResponse;
import vn.onltest.service.QuestionService;
import vn.onltest.util.PathUtil;
import vn.onltest.util.SwaggerUtil;

import javax.validation.Valid;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/questions")
@PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
@AllArgsConstructor
@Api(tags = "Question")
public class QuestionController {
    private final QuestionService questionService;

    @ApiOperation(value = "Create a question", response = BaseResultResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = SwaggerUtil.STATUS_200_MESSAGE),
            @ApiResponse(code = 401, message = SwaggerUtil.STATUS_401_REASON),
            @ApiResponse(code = 403, message = SwaggerUtil.STATUS_403_REASON),
            @ApiResponse(code = 404, message = SwaggerUtil.STATUS_404_REASON),
            @ApiResponse(code = 500, message = SwaggerUtil.STATUS_500_REASON)
    })
    @PostMapping("create")
    public AbstractResponse createQuestion(@Valid @RequestBody Question questionRequest,
                                           BindingResult bindingResult) throws CustomMethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new CustomMethodArgumentNotValidException(bindingResult);
        } else {
            Question createdQuestion = questionService.createQuestion(questionRequest);
            return new BaseResultResponse<>(HttpStatus.OK.value(), createdQuestion);
        }
    }
}
