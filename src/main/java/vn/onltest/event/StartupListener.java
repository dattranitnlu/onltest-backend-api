package vn.onltest.event;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import vn.onltest.event.constant.ActionConstants;

import java.util.Date;

@Component
@AllArgsConstructor
public class StartupListener implements ApplicationRunner {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send(ActionConstants.CREATED, msg);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        for (int i = 0; i < 1000; i++) {
//            sendMessage("Now: " + new Date());
//            Thread.sleep(3000);
//        }
    }
}
