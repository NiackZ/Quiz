package quiz.studios;

import lombok.RequiredArgsConstructor;
import quiz.utils.model.LongString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@RequiredArgsConstructor
@Table(name = "studios")
public class Studio extends LongString {
}
