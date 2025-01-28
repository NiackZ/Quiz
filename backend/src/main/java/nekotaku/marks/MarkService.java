package nekotaku.marks;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;

    public List<Mark> findAll() {
        return IteratorUtils.toList(this.markRepository.findAll().iterator());
    }
}
