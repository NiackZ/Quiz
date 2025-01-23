package nekotaku.quizzes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import nekotaku.quizzes.model.Quiz;

public interface IQuizRepository extends JpaRepository<Quiz, Long> {

}
