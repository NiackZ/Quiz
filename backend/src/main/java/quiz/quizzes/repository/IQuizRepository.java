package quiz.quizzes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quiz.quizzes.entity.Quiz;

public interface IQuizRepository extends JpaRepository<Quiz, Long> {

}
