package nekotaku.types;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nekotaku.utils.model.LongString;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "types")
public class Type extends LongString {
}
