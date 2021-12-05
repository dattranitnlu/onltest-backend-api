package vn.onltest.event;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import vn.onltest.event.constant.ActionConstants;

@AllArgsConstructor
public class GroupListener {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send("test", msg);
    }

    @KafkaListener(topics = ActionConstants.GET, groupId = "group-id")
    public void listen(String message) {
        System.out.println("Received Message in group - group-id: " + message);
    }
}
