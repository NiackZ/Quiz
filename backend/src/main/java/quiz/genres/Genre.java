package quiz.genres;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import quiz.utils.model.LongString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "genres", uniqueConstraints = { @UniqueConstraint(columnNames = {"name"})})
public class Genre extends LongString {
}
