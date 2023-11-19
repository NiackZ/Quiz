package quiz.status;

import lombok.RequiredArgsConstructor;
import quiz.utils.model.LongString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@RequiredArgsConstructor
@Table(name = "statuses")
public class Status extends LongString {
}
