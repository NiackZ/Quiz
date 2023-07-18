package quiz.answers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quiz.answers.model.Answer;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
}
