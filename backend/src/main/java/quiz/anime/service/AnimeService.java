package quiz.anime.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import quiz.anime.Anime;
import quiz.anime.repository.AnimeRepository;

@Service
@AllArgsConstructor
public class AnimeService {
    private final AnimeRepository repository;
    public Long create(Anime anime) {
        return this.repository.save(anime).getId();
    }
}
