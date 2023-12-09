package quiz.anime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import quiz.anime.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    @Transactional
    @Modifying
    @Query("update Anime a set a.posterURL = ?1 where a.id = ?2")
    void updatePoster(String posterURL, Long id);

}
