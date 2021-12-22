package vn.onltest.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TestModelRequest {
    private int testCodeQuantity;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int duration;
    private String subjectName;
}
