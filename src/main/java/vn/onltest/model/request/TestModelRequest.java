package vn.onltest.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Test Model Request")
public class TestModelRequest {
    private int testCodeQuantity;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int duration;
    private String subjectName;

    private List<QuestionModelRequest> questions;
}
