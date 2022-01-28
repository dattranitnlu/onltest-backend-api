package vn.onltest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.onltest.entity.AnswerSheet;

public interface AnswersSheetRepository extends JpaRepository<AnswerSheet, Long> {
}
