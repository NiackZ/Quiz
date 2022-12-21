package quiz.answers.entity;

import lombok.*;
import quiz.questions.entity.Question;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "answers")
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String text;

  @ManyToOne
  private Question question;

  private String result;
}
