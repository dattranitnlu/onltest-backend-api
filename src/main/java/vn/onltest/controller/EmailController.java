package vn.onltest.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.onltest.service.EmailForgotPassword;
import vn.onltest.util.PathUtil;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping(PathUtil.BASE_PATH + "/utils")
@AllArgsConstructor
@Api(tags = "Utils api")
public class EmailController {
    private final EmailForgotPassword emailForgotPassword;

    @PostMapping("/sentEmail")
    public ResponseEntity<?> createUser() throws MessagingException, IOException {
        emailForgotPassword.sendEmail("Dat Tran", "www.google.com", "dattranitnlu@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree("{\"Greeting\": \"Greetings from Spring Boot!\"}");
        return ResponseEntity.ok(json);
    }
}
