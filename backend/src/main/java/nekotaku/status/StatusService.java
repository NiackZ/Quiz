package nekotaku.status;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusService {

    private final StatusRepository repository;

    private final String NOT_FOUND = "Статус не найден. ID = ";

    public List<Status> findAll() {
        return IteratorUtils.toList(this.repository.findAll().iterator());
    }

    public Status save(Status status) {
        return repository.save(status);
    }

    public Status update(Long id, Status updatedStatus) {
        return repository.findById(id)
                .map(item -> {
                    item.setName(updatedStatus.getName());
                    item.setDeleted(updatedStatus.isDeleted());
                    return repository.save(item);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
    }

    public void deleteById(Long id) {
        Status status = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        status.setDeleted(true);
        repository.save(status);
    }

}
