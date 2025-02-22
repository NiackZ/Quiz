package nekotaku.studios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioRepository extends JpaRepository<Studio, Long> {
    @Override
    List<Studio> findAllById(Iterable<Long> longs);
    boolean existsByName(String name);
}
