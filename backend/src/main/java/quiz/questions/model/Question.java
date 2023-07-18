package quiz.questions.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import quiz.answers.model.Answer;
import quiz.quizzes.model.Quiz;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "questions")
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String text;

  @ManyToOne(fetch = FetchType.LAZY)
  private Quiz quiz;

  @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
  private List<Answer> answers;

  @OneToOne
  @ColumnDefault("null")
  private Answer answerKey;
}
