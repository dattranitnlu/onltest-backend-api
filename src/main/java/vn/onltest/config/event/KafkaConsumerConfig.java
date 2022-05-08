package vn.onltest.config.event;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import vn.onltest.model.request.SaveAnswerRequest;
import vn.onltest.service.TestService;

@Service
@AllArgsConstructor
public class KafkaConsumerConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);
    private final TestService testService;

    @KafkaListener(topics = "answer", groupId = "group-id")
    public void listenAnswer(@Payload SaveAnswerRequest data) {
        System.out.println(data.getUsername());
        logger.info(String.format("Save into answer sheet with testId = {}, questionId = {}, optionId = {}", data.getTestId(), data.getQuestionId(), data.getOptionId()));
        testService.saveAnswer(data.getUsername(), data.getTestId(), data.getQuestionId(), data.getOptionId());
    }
}