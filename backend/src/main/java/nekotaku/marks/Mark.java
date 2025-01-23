package nekotaku.marks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nekotaku.utils.model.LongString;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name="marks")
public class Mark extends LongString {
}
