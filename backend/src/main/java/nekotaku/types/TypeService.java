package nekotaku.types;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeService {

    private final TypeRepository repository;
    private final String NOT_FOUND = "Тип не найден. ID = ";
    private final String EXIST = "Тип с таким именем уже существует.";

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
        Type existingType = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));

        boolean isNameChanged = !existingType.getName().equalsIgnoreCase(updatedType.getName());

        if (isNameChanged && repository.existsByName(updatedType.getName())) {
            throw new EntityExistsException(EXIST);
        }

        existingType.setName(updatedType.getName());
        existingType.setDeleted(updatedType.isDeleted());

        return repository.save(existingType);
    }

    public void deleteById(Long id) {
        Type type = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        type.setDeleted(true);
        repository.save(type);
    }
}
