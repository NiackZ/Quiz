package quiz.types;

import liquibase.repackaged.org.apache.commons.collections4.IteratorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class TypeService {

    private final TypeRepository repository;
    private static final String NOT_FOUND = "Тип не найден. ID = ";

    public List<Type> findAll() {
        return IteratorUtils.toList(this.repository.findAll().iterator());
    }

    public Type save(Type type) {
        return repository.save(type);
    }

    public Type update(Long id, Type updatedType) {
        return repository.findById(id)
                .map(item -> {
                    item.setName(updatedType.getName());
                    item.setDeleted(updatedType.isDeleted());
                    return repository.save(item);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
    }

    public void deleteById(Long id) {
        Type type = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        type.setDeleted(true);
        repository.save(type);
    }
}
