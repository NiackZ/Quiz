package quiz.types;

import liquibase.repackaged.org.apache.commons.collections4.IteratorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class TypeService {

    private final TypeRepository repository;
    private static final String NOT_FOUND = "Тип не найден. ID = ";
    private static final String EXIST = "Тип с таким именем уже существует.";

    public List<Type> findAll() {
        return IteratorUtils.toList(this.repository.findAll().iterator());
    }

    public Type save(Type type) {
        if (repository.existsByName(type.getName())) {
            throw new EntityExistsException(EXIST);
        }
        return repository.save(type);
    }

    public Type update(Long id, Type updatedType) {
        if (repository.existsByName(updatedType.getName())) {
            throw new EntityExistsException(EXIST);
        }
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
