package nekotaku.marks;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MarkRepository extends CrudRepository<Mark, Long> {
    @Override
    List<Mark> findAllById(Iterable<Long> longs);
}
