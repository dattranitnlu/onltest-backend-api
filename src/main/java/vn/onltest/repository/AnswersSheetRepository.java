package vn.onltest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.onltest.model.entity.AnswerSheet;

import java.util.Optional;

public interface AnswersSheetRepository extends JpaRepository<AnswerSheet, Long> {

    @Query("select ansh from AnswerSheets ansh where ansh.testingResult.id = ?1 and ansh.question.id = ?2")
    Optional<AnswerSheet> findByTestingResultAndQuestionAndOptionId(long testResultId,
                                                                    long questionId);

    @Query("select SUM(ansh.grade) from AnswerSheets ansh where ansh.testingResult.id = ?1")
    double findTotalGradeByTestingResultId(long testingResultId);

    @Query("select count(ansh) from AnswerSheets ansh where ansh.testingResult.id = ?1 and ansh.grade <> 0")
    int countCorrectAnswerNumberByTestingResultId(long testingResultId);

    @Query("select count(ansh) from AnswerSheets ansh where ansh.testingResult.id = ?1")
    int countTotalQuestionByTestingResultId(long testingResultId);
}
