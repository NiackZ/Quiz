package quiz.genres;

import liquibase.repackaged.org.apache.commons.collections4.IteratorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private static final String NOT_FOUND = "Жанр не найден. ID = ";

    public List<Genre> findAll() {
        return IteratorUtils.toList(this.genreRepository.findAll().iterator());
    }

    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre update(Long id, Genre updatedGenre) {
        return genreRepository.findById(id)
                .map(genre -> {
                    genre.setName(updatedGenre.getName());
                    return genreRepository.save(genre);
                })
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
    }

    public void deleteById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND + id));
        genre.setDeleted(true);
        genreRepository.save(genre);
    }
}
