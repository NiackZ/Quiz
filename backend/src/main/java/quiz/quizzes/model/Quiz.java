package quiz.quizzes.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import quiz.questions.model.Question;
import quiz.users.model.User;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "quizzes")
public class Quiz {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
  private List<Question> questions;

  @ColumnDefault("false")
  private boolean visible;

  @ColumnDefault("false")
  private boolean deleted;

}
