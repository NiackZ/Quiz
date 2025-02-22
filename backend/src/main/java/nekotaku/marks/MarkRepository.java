package nekotaku.marks;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    @Override
    List<Mark> findAllById(Iterable<Long> longs);
}
