package nekotaku.genres;

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
public class GenreService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String CACHE_NAME = "genres";
    private final String CACHE_KEY = "genre:";
    private final int TTL = 30;
    private final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    private final GenreRepository repository;
    private final String NOT_FOUND = "Жанр не найден. ID = ";
    private final String EXIST = "Жанр с таким именем уже существует.";

    public List<Genre> findAll() {
        return IteratorUtils.toList(this.repository.findAll().iterator());
    }

    @CachePut(value = CACHE_NAME, key = "#result.id")
    public Genre save(Genre genre) {
        if (repository.existsByName(genre.getName())) {
            throw new EntityExistsException(EXIST);
        }
        Genre savedGenre = repository.save(genre);
        redisTemplate.opsForValue().set(CACHE_KEY + savedGenre.getId(), savedGenre, TTL, TIME_UNIT);
        return savedGenre;
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public Genre update(Long id, Genre updatedGenre) {
        Genre existingGenre = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));

        boolean isNameChanged = !existingGenre.getName().equalsIgnoreCase(updatedGenre.getName());
        if (isNameChanged && repository.existsByName(updatedGenre.getName())) {
            throw new EntityExistsException(EXIST);
        }

        existingGenre.setName(updatedGenre.getName());
        existingGenre.setDeleted(updatedGenre.isDeleted());
        redisTemplate.opsForValue().set(CACHE_KEY + id, existingGenre, TTL, TIME_UNIT);
        return repository.save(existingGenre);
    }

    @CachePut(value = CACHE_NAME, key = "#id")
    public void deleteById(Long id) {
        Genre genre = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        genre.setDeleted(true);
        repository.save(genre);
        redisTemplate.delete(CACHE_KEY + id);
    }

}
