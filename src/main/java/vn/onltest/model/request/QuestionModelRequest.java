package vn.onltest.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Question Model Request")
public class QuestionModelRequest {
    private String question;
    private double mark;
    private boolean isShuffle;
    private String questionType;
    private List<OptionModelRequest> options;

}
