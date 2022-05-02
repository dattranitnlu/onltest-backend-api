package vn.onltest.model.response.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestingResultData {
    double totalGrade;
    int correctAnswerNumber;
    int totalQuestion;
}
