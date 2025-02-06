package nekotaku.studios;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudioRepository extends CrudRepository<Studio, Long> {
    @Override
    List<Studio> findAllById(Iterable<Long> longs);
    boolean existsByName(String name);
}
