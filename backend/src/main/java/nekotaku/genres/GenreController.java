package nekotaku.genres;


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
    public ResponseEntity<Genre> create(@RequestBody Genre genre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(genre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> update(@PathVariable Long id, @RequestBody Genre genre) {
        return ResponseEntity.ok(service.update(id, genre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
