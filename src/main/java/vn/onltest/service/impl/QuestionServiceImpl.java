package vn.onltest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.onltest.model.entity.Question;
import vn.onltest.repository.QuestionRepository;
import vn.onltest.service.QuestionService;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }
}
