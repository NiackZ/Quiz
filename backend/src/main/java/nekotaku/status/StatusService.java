package nekotaku.status;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class StatusService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String CACHE_NAME = "statuses";
    private final String CACHE_KEY = "status:";
    private final int TTL = 30;
    private final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final StatusRepository repository;
    private final String NOT_FOUND = "Статус не найден. ID = ";
    private final String EXIST = "Статус с таким именем уже существует.";

    public List<Status> findAll() {
        return IteratorUtils.toList(this.repository.findAll().iterator());
    }

    @CachePut(value = CACHE_NAME, key = "#result.id")
    public Status save(Status status) {
        if (repository.existsByName(status.getName())) {
            throw new EntityExistsException(EXIST);
        }
        Status savedStatus = repository.save(status);
        redisTemplate.opsForValue().set(CACHE_KEY + savedStatus.getId(), savedStatus, TTL, TIME_UNIT);
        return savedStatus;
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public Status update(Long id, Status updatedStatus) {
        Status status = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));

        boolean isNameChanged = !status.getName().equalsIgnoreCase(updatedStatus.getName());

        if (isNameChanged && repository.existsByName(updatedStatus.getName())) {
            throw new EntityExistsException(EXIST);
        }

        status.setName(updatedStatus.getName());
        status.setDeleted(updatedStatus.isDeleted());
        redisTemplate.opsForValue().set(CACHE_KEY + id, status, TTL, TIME_UNIT);
        return repository.save(status);
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public void deleteById(Long id) {
        Status status = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        status.setDeleted(true);
        repository.save(status);
        redisTemplate.delete(CACHE_KEY + id);
    }

}
