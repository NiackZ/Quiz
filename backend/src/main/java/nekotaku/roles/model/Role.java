package nekotaku.roles.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import nekotaku.utils.model.LongString;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "roles")
public class Role extends LongString {
}