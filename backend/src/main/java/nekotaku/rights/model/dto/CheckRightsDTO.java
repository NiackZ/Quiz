package nekotaku.rights.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckRightsDTO {
    private Long userId;
    private List<String> rights;
}
