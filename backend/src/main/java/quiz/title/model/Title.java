package quiz.title.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Title {
    private String ruName;
    private String enName;
    private String originalName;
    private String posterURL;
    private String type;
}
