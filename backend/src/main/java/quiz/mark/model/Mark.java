package quiz.mark.model;

import lombok.RequiredArgsConstructor;
import quiz.utils.model.LongString;

import javax.persistence.Entity;
import javax.persistence.Table;

@RequiredArgsConstructor
@Entity
@Table(name="marks")
public class Mark extends LongString {
}
