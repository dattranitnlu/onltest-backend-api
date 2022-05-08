package vn.onltest.config.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.onltest.model.request.SaveAnswerRequest;

@Service
public class KafkaProducerConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    @Autowired
    private KafkaTemplate<String, SaveAnswerRequest> kafkaTemplate;

    public void sendMessage(SaveAnswerRequest saveAnswerRequest) {
        logger.info(String.format("#### -> Producing message -> {}", saveAnswerRequest.toString()));
        kafkaTemplate.send("answer", saveAnswerRequest);
    }
}