package quiz.roles.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import quiz.utils.model.LongString;

import javax.persistence.*;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "roles")
public class Role extends LongString {
}