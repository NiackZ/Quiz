package nekotaku.genres;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
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
public class GenreService {

    private final Logger logger = LoggerFactory.getLogger(GenreService.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final String CACHE_NAME = "genres";
    private final String CACHE_KEY = "genre:";
    private final int TTL = 30;
    private final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final GenreRepository repository;
    private final String NOT_FOUND = "Жанр не найден. ID = ";
    private final String EXIST = "Жанр с таким именем уже существует.";

    public List<Genre> findAll() {
        return repository.findAll();
    }

    @CachePut(value = CACHE_NAME, key = "#result.id")
    public Genre save(Genre genre) {
        try {
            if (repository.existsByName(genre.getName())) {
                throw new EntityExistsException(EXIST);
            }
            Genre savedGenre = repository.save(genre);
            redisTemplate.opsForValue().set(CACHE_KEY + savedGenre.getId(), savedGenre, TTL, TIME_UNIT);
            return savedGenre;
        } catch (EntityExistsException e) {
            logger.error("Жанр с именем '{}' уже существует. ", genre.getName(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Произошла ошибка при сохранении жанра: {}", genre.getName(), e);
            throw new IllegalArgumentException("Не удалось создать новый жанр", e);
        }
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public Genre update(Long id, Genre updatedGenre) {
        try {
            Genre genre = repository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));

            boolean isNameChanged = !genre.getName().equalsIgnoreCase(updatedGenre.getName());

            if (isNameChanged && repository.existsByName(updatedGenre.getName())) {
                throw new EntityExistsException(EXIST);
            }

            genre.setName(updatedGenre.getName());
            genre.setDeleted(updatedGenre.isDeleted());
            redisTemplate.opsForValue().set(CACHE_KEY + id, genre, TTL, TIME_UNIT);
            return repository.save(genre);
        }
        catch (EntityNotFoundException e) {
            logger.error("Жанр с ИД {} не найден.", updatedGenre.getId(), e);
            throw e;
        }
        catch (EntityExistsException e) {
            logger.error("Жанр с именем '{}' уже существует.", updatedGenre.getName(), e);
            throw e;
        }
        catch (Exception e) {
            logger.error("Произошла ошибка при сохранении жанра: {}", updatedGenre.getName(), e);
            throw new IllegalArgumentException("Не удалось сохранить жанр", e);
        }
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public void deleteById(Long id) {
        try {
            Genre genre = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
            genre.setDeleted(true);
            repository.save(genre);
            redisTemplate.delete(CACHE_KEY + id);
        }
        catch (EntityNotFoundException e) {
            logger.error("Жанр с ИД {} не найден.", id, e);
            throw e;
        }
        catch (Exception e) {
            logger.error("Произошла ошибка при удалении жанра с ИД {}", id, e);
            throw new IllegalArgumentException("Не удалось удалить жанр", e);
        }
    }

}
