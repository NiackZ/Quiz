package nekotaku.questions.model;

import jakarta.persistence.*;
import lombok.*;
import nekotaku.answers.model.Answer;
import nekotaku.quizzes.model.Quiz;
import org.hibernate.annotations.ColumnDefault;

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
