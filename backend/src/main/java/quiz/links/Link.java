package quiz.links;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import quiz.utils.model.LongString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="links")
public class Link extends LongString {
    private String url;
}
