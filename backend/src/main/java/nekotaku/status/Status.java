package nekotaku.status;

import lombok.RequiredArgsConstructor;
import nekotaku.utils.model.LongString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@RequiredArgsConstructor
@Table(name = "statuses")
public class Status extends LongString {
}
