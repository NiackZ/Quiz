package nekotaku.genres;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nekotaku.utils.model.LongString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "genres", uniqueConstraints = { @UniqueConstraint(columnNames = {"name"})})
public class Genre extends LongString {
}
