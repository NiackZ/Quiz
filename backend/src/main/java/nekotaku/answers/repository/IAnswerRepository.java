package nekotaku.answers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import nekotaku.answers.model.Answer;

public interface IAnswerRepository extends JpaRepository<Answer, Long> {
}
