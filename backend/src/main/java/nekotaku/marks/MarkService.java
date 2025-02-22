package nekotaku.marks;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;

    public List<Mark> findAll() {
        return markRepository.findAll();
    }
}
