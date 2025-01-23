package nekotaku.questions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import nekotaku.questions.model.Question;

public interface IQuestionRepository extends JpaRepository<Question, Long> {
}
