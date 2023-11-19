package quiz.anime.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiz.anime.Anime;
import quiz.anime.service.AnimeService;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/anime")
@AllArgsConstructor
public class AnimeController {
    private final AnimeService service;

    @PostMapping()
    public Long create(@RequestBody @NotNull Anime anime) {
        return this.service.create(anime);
    }

}
