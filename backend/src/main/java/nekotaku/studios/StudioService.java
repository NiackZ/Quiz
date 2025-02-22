package nekotaku.studios;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import nekotaku.status.Status;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class StudioService {

    private final Logger logger = LoggerFactory.getLogger(StudioService.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final String CACHE_NAME = "studios";
    private final String CACHE_KEY = "studio:";
    private final int TTL = 30;
    private final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final StudioRepository repository;
    private final String NOT_FOUND = "Студия не найден. ID = ";
    private final String EXIST = "Студия с таким именем уже существует.";

    public List<Studio> findAll() {
        return repository.findAll();
    }

    @CachePut(value = CACHE_NAME, key = "#result.id")
    public Studio save(Studio studio) {
        try {
            if (repository.existsByName(studio.getName())) {
                throw new EntityExistsException(EXIST);
            }
            Studio savedStudio = repository.save(studio);
            redisTemplate.opsForValue().set(CACHE_KEY + savedStudio.getId(), savedStudio, TTL, TIME_UNIT);
            return savedStudio;
        } catch (EntityExistsException e) {
            logger.error("Студия с именем '{}' уже существует. ", studio.getName(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Произошла ошибка при сохранении студии: {}", studio.getName(), e);
            throw new IllegalArgumentException("Не удалось создать новую студию", e);
        }
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public Studio update(Long id, Studio updatedStudio) {
        try {
            Studio studio = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));

            boolean isNameChanged = !studio.getName().equalsIgnoreCase(updatedStudio.getName());

            if (isNameChanged && repository.existsByName(updatedStudio.getName())) {
                throw new EntityExistsException(EXIST);
            }

            studio.setName(updatedStudio.getName());
            studio.setDeleted(updatedStudio.isDeleted());
            redisTemplate.opsForValue().set(CACHE_KEY + id, studio, TTL, TIME_UNIT);
            return repository.save(studio);
        }
        catch (EntityNotFoundException e) {
            logger.error("Студия с ИД {} не найден.", updatedStudio.getId(), e);
            throw e;
        }
        catch (EntityExistsException e) {
            logger.error("Студия с именем '{}' уже существует.", updatedStudio.getName(), e);
            throw e;
        }
        catch (Exception e) {
            logger.error("Произошла ошибка при сохранении студии: {}", updatedStudio.getName(), e);
            throw new IllegalArgumentException("Не удалось сохранить студию", e);
        }
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public void deleteById(Long id) {
        try {
            Studio studio = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
            studio.setDeleted(true);
            repository.save(studio);
            redisTemplate.delete(CACHE_KEY + id);
        }
        catch (EntityNotFoundException e) {
            logger.error("Студия с ИД {} не найден.", id, e);
            throw e;
        }
        catch (Exception e) {
            logger.error("Произошла ошибка при удалении студии с ИД {}", id, e);
            throw new IllegalArgumentException("Не удалось удалить студию", e);
        }
    }
}
