package nekotaku.types;

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
public class TypeService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String CACHE_NAME = "types";
    private final String CACHE_KEY = "type:";
    private final int TTL = 30;
    private final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final TypeRepository repository;
    private final String NOT_FOUND = "Тип не найден. ID = ";
    private final String EXIST = "Тип с таким именем уже существует.";

    public List<Type> findAll() {
        return IteratorUtils.toList(this.repository.findAll().iterator());
    }

    @CachePut(value = CACHE_NAME, key = "#result.id")
    public Type save(Type type) {
        //todo убрать дублирование кода в других классах
        if (repository.existsByName(type.getName())) {
            throw new EntityExistsException(EXIST);
        }
        Type savedType = repository.save(type);
        redisTemplate.opsForValue().set(CACHE_KEY + savedType.getId(), savedType, TTL, TIME_UNIT);
        return savedType;
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public Type update(Long id, Type updatedType) {
        Type existingType = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));

        boolean isNameChanged = !existingType.getName().equalsIgnoreCase(updatedType.getName());
        if (isNameChanged && repository.existsByName(updatedType.getName())) {
            throw new EntityExistsException(EXIST);
        }

        existingType.setName(updatedType.getName());
        existingType.setDeleted(updatedType.isDeleted());
        redisTemplate.opsForValue().set(CACHE_KEY + id, existingType, TTL, TIME_UNIT);
        return repository.save(existingType);
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public void deleteById(Long id) {
        Type type = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        type.setDeleted(true);
        repository.save(type);
        redisTemplate.delete(CACHE_KEY + id);
    }

}
