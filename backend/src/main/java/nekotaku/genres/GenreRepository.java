package nekotaku.genres;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    @Override
    List<Genre> findAllById(Iterable<Long> longs);
}
