package nekotaku.anime.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nekotaku.anime.Anime;
import nekotaku.anime.AnimeCreateDTO;
import nekotaku.anime.AnimeGetDTO;
import nekotaku.anime.AnimeGetShortDTO;
import nekotaku.anime.service.AnimeService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/anime")
@AllArgsConstructor
public class AnimeController {
    private final AnimeService animeService;

    //Получение всех аниме
    @GetMapping
    public ResponseEntity<List<AnimeGetDTO>> getAll() {
        List<AnimeGetDTO> animes = this.animeService.getAllAnimes();
        return new ResponseEntity<>(animes, HttpStatus.OK);
    }

    // Укороченная информация
    @GetMapping("/short")
    public ResponseEntity<List<AnimeGetShortDTO>> getAllShortInfo() {
        List<AnimeGetShortDTO> animes = this.animeService.getAllAnimesShort();
        return new ResponseEntity<>(animes, HttpStatus.OK);
    }

    // Получить одно аниме по ID
    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long id) {
        Anime anime = this.animeService.getAnimeById(id);
        return new ResponseEntity<>(anime, HttpStatus.OK);
    }

    // Создать новое аниме
    @PostMapping
    public ResponseEntity<Long> createAnime(@RequestBody @NotNull AnimeCreateDTO anime) throws IOException {
        Long createdAnimeId = this.animeService.createAnime(anime);
        return new ResponseEntity<>(createdAnimeId, HttpStatus.CREATED);
    }

    // Обновить существующее аниме
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnime(@PathVariable Long id, @RequestBody AnimeCreateDTO anime) throws InterruptedException, IOException {
        this.animeService.updateAnime(id, anime);
        Thread.sleep(2000);// имитация долгой обработки
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Удалить аниме
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        animeService.deleteAnime(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
