package quiz.status;

import liquibase.repackaged.org.apache.commons.collections4.IteratorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public List<Status> findAll() {
        return IteratorUtils.toList(this.statusRepository.findAll().iterator());
    }
}
