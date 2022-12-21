package quiz.questions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quiz.questions.entity.Question;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
}
