package vn.onltest.controller.user;

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

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/questions")
@PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

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
