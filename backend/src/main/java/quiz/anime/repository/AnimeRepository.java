package quiz.anime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quiz.anime.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
