package vn.onltest.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class SaveAnswerRequest implements Serializable {
    private String username;
    private Long testId;
    private Long questionId;
    private Long optionId;
}
