package quiz.users.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import quiz.users.model.User;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGetDTO {

  @NotNull
  private Long id;
  @NotNull
  private String username;
  @NotNull
  private String email;

  private String url;

  //private List<QuizGetDTO> quizzes = new ArrayList<>();

  private boolean isActive;

  public UserGetDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.isActive = user.isActive();
    this.url = user.getAvatarURL();
    //this.quizzes = null;
  }

//  public void setQuizzes(List<Quiz> quizzes) {
//    this.quizzes = quizzes.stream().map(quiz -> {
//      QuizGetDTO quizGetDTO = new QuizGetDTO(quiz);
//      quizGetDTO.setQuestions(quiz.getQuestions());
//      return quizGetDTO;
//    }).toList();
//  }
}
