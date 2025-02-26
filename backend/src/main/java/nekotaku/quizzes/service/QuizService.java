package quiz.quizzes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import quiz.questions.api.dto.QuestionGetDTO;
import quiz.quizzes.api.dto.QuizCreateDTO;
import quiz.quizzes.api.dto.QuizGetDTO;
import quiz.quizzes.entity.Quiz;
import quiz.quizzes.repository.IQuizRepository;
import quiz.users.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Service
public class QuizService {
  @Autowired
  private IQuizRepository quizRepository;
  @Autowired
  private UserService userService;

  private QuizGetDTO quizToQuizGetDTO(Quiz quiz){
    QuizGetDTO quizGetDTO = new QuizGetDTO(quiz);
    quizGetDTO.setAuthor(quiz.getAuthor());
    quizGetDTO.setQuestions(quiz.getQuestions());
    return quizGetDTO;
  }

  public Quiz findById(@NotNull Long id){
    return this.quizRepository.findById(id).orElseThrow(
        () -> new RuntimeException(String.format("Опрос с ИД %d не найден", id))
    );
  }

  public QuizGetDTO getById(@NotNull Long id){
    //todo убрать пароль
    return quizToQuizGetDTO(findById(id));
  }

  public List<QuizGetDTO> findAll(){
    //todo убрать пароль
    return this.quizRepository.findAll().stream().map(this::quizToQuizGetDTO).toList();
  }

  public Long add(@NotNull @Valid QuizCreateDTO quizCreateDTO) {
    Quiz quiz = quizCreateDTO.getId() == null ? new Quiz() : findById(quizCreateDTO.getId());

    quiz.setTitle(quizCreateDTO.getTitle());
    quiz.setAuthor(this.userService.findById(quizCreateDTO.getAuthorId()));
    quiz.setDeleted(quizCreateDTO.isDeleted());
    quiz.setVisible(quizCreateDTO.isVisible());

    return this.quizRepository.save(quiz).getId();
  }

  public Long update(@NotNull Long id, @NotNull @Valid QuizCreateDTO quizCreateDTO){
    quizCreateDTO.setId(id);
    return add(quizCreateDTO);
  }

  public Long delete(@NotNull Long id, @NotNull @Valid QuizCreateDTO quizCreateDTO){
    quizCreateDTO.setId(id);
    quizCreateDTO.setDeleted(true);
    return add(quizCreateDTO);
  }

  public QuizGetDTO getResult(@NotNull Long quizId){
    Quiz quiz = findById(quizId);
    QuizGetDTO quizGetDTO = new QuizGetDTO(quiz);
    quizGetDTO.setQuestionsFromDTO(quiz.getQuestions().stream().map(question -> {
      QuestionGetDTO questionGetDTO = new QuestionGetDTO(question);
      questionGetDTO.setAnswers(question.getAnswers());
      if (question.getAnswerKey() != null)
        questionGetDTO.setAnswerKey(question.getAnswerKey());
      return questionGetDTO;
    }).toList());
    return quizGetDTO;
  }

}
