package quiz.questions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quiz.questions.model.Question;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
}
