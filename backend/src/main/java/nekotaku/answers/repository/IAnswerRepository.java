package quiz.answers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quiz.answers.entity.Answer;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
}
