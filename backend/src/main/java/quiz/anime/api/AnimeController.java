package quiz.anime.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiz.anime.AnimeCreateDTO;
import quiz.anime.service.AnimeService;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("/anime")
@AllArgsConstructor
public class AnimeController {
    private final AnimeService service;

    @PostMapping()
    public Long create(@RequestBody @NotNull AnimeCreateDTO anime) throws IOException {
        return this.service.create(anime);
    }

}
