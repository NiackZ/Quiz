package nekotaku.genres;

import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {

    private final GenreRepository repository;
    private final String NOT_FOUND = "Жанр не найден. ID = ";

    public List<Genre> findAll() {
        return IteratorUtils.toList(this.repository.findAll().iterator());
    }

    public Genre save(Genre genre) {
        return repository.save(genre);
    }

    public Genre update(Long id, Genre updatedGenre) {
        return repository.findById(id)
                .map(item -> {
                    item.setName(updatedGenre.getName());
                    item.setDeleted(updatedGenre.isDeleted());
                    return repository.save(item);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
    }

    public void deleteById(Long id) {
        Genre genre = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        genre.setDeleted(true);
        repository.save(genre);
    }
}
