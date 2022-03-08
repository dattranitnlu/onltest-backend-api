package vn.onltest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.onltest.model.entity.AnswerSheet;

import java.util.Optional;

public interface AnswersSheetRepository extends JpaRepository<AnswerSheet, Long> {

    @Query("select ansh from AnswerSheets ansh where ansh.testingResult.id = ?1 and ansh.question.id = ?2")
    Optional<AnswerSheet> findByTestingResultAndAndQuestionAndOptionId(long testResultId,
                                                                       long questionId);
}
