package nekotaku.genres;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByName(String name);
    @Override
    List<Genre> findAllById(Iterable<Long> longs);
}
