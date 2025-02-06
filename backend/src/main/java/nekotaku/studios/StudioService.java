package nekotaku.studios;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudioService {

    private final StudioRepository repository;

    private final String NOT_FOUND = "Студия не найден. ID = ";
    private final String EXIST = "Студия с таким именем уже существует.";

    public List<Studio> findAll() {
        return IteratorUtils.toList(this.repository.findAll().iterator());
    }

    public Studio save(Studio studio) {
        return repository.save(studio);
    }

    public Studio update(Long id, Studio updatedStudio) {
        Studio studio = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));

        boolean isNameChanged = !studio.getName().equalsIgnoreCase(updatedStudio.getName());

        if (isNameChanged && repository.existsByName(updatedStudio.getName())) {
            throw new EntityExistsException(EXIST);
        }

        studio.setName(updatedStudio.getName());
        studio.setDeleted(updatedStudio.isDeleted());

        return repository.save(studio);

    }

    public void deleteById(Long id) {
        Studio studio = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        studio.setDeleted(true);
        repository.save(studio);
    }
}
