package nekotaku.studios;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import nekotaku.utils.model.LongString;

@Entity
@RequiredArgsConstructor
@Table(name = "studios")
public class Studio extends LongString {
}
