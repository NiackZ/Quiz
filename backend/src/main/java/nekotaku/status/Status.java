package nekotaku.status;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import nekotaku.utils.model.LongString;


@Entity
@RequiredArgsConstructor
@Table(name = "statuses")
public class Status extends LongString {
}
