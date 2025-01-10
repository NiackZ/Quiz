package quiz.studios;

import liquibase.repackaged.org.apache.commons.collections4.IteratorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudioService {

    private final StudioRepository repository;

    public List<Studio> findAll() {
        return IteratorUtils.toList(this.repository.findAll().iterator());
    }
}
