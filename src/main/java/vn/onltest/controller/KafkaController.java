package vn.onltest.controller;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.onltest.util.PathUtil;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/testKafka")
@AllArgsConstructor
public class KafkaController {
    private final KafkaTemplate kafkaTemplate;

    @GetMapping()
    public String sendMessage(@RequestParam(name = "message") String message) {
        kafkaTemplate.send("dresses", message);
        return "OK";
    }
}
