package quiz.anime.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import quiz.anime.AnimeCreateDTO;
import quiz.anime.AnimeGetDTO;
import quiz.anime.service.AnimeService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/anime")
@AllArgsConstructor
public class AnimeController {
    private final AnimeService service;

    @PostMapping()
    public Long create(@RequestBody @NotNull AnimeCreateDTO anime) throws IOException {
        return this.service.create(anime);
    }

    @GetMapping()
    public List<AnimeGetDTO> getAll() {
        return this.service.getAll();
    }

}
