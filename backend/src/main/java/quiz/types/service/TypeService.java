package quiz.types.service;

import liquibase.repackaged.org.apache.commons.collections4.IteratorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import quiz.types.model.Type;
import quiz.types.repository.TypeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;
    public List<Type> findAll() {
        return IteratorUtils.toList(this.typeRepository.findAll().iterator());
    }
}
