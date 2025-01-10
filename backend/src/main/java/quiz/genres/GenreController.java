package quiz.genres;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {

    private final GenreService service;

    @GetMapping
    public List<Genre> getAll() {
        return this.service.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Genre genre) {
        try {
            Genre savedGenre = service.save(genre);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    String.format("Ошибка при добавлении жанра \"%s\"", genre.getName())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Genre genre) {
        try {
            Genre savedGenre = service.update(id, genre);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedGenre);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    String.format("Ошибка при сохранении жанра \"%s\" с ИД = %d", genre.getName(), id)
            );
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
