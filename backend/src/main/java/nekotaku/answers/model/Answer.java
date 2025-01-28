package nekotaku.answers.model;

import jakarta.persistence.*;
import lombok.*;
import nekotaku.questions.model.Question;

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
