package quiz.questions.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import quiz.answers.api.dto.AnswerGetDTO;
import quiz.answers.entity.Answer;
import quiz.questions.entity.Question;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionGetDTO {

  @NotNull
  private Long id;
  @NotNull
  private String text;
  @NotNull
  private List<AnswerGetDTO> answers;

  private AnswerGetDTO answerKey = null;

  public QuestionGetDTO(Question questionData){
    this.id = questionData.getId();
    this.text = questionData.getText();
  }

  public void setAnswers(List<Answer> answers) {
    this.answers = answers.stream().map(AnswerGetDTO::new).toList();
  }

  public void setAnswerKey(Answer answer){
    this.answerKey = new AnswerGetDTO(answer);
  }
}
