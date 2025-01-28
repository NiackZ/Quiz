package nekotaku.genres;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import nekotaku.utils.model.LongString;

@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "genres", uniqueConstraints = { @UniqueConstraint(columnNames = {"name"})})
public class Genre extends LongString {
}
