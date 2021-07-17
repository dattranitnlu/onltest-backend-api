package vn.onltest.service.impl;

import org.springframework.stereotype.Service;
import vn.onltest.entity.Question;
import vn.onltest.repository.QuestionRepository;
import vn.onltest.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }
}
