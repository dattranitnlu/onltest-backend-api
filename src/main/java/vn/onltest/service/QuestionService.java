package vn.onltest.service;

import org.springframework.transaction.annotation.Transactional;
import vn.onltest.model.entity.Question;

public interface QuestionService {

    @Transactional
    Question createQuestion(Question question);
}
