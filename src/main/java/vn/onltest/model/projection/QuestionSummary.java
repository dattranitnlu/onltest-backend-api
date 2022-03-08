package vn.onltest.model.projection;

import vn.onltest.model.entity.QuestionType;

import java.util.Date;
import java.util.List;

public interface QuestionSummary {
    long getId();
    String getQuestion();
    double getMark();
    boolean isShuffle();
    int getStatus();
    Date getCreatedDate();
    Date getUpdatedDate();
    QuestionType getQuestionType();

    List<OptionSummary> getOptionList();
}
