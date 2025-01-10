package quiz.studios;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studios")
@AllArgsConstructor
public class StudioController {

    private final StudioService service;

    @GetMapping
    public List<Studio> getAll() {
        return this.service.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Studio studio) {
        try {
            Studio saved = service.save(studio);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    String.format("Ошибка при добавлении жанра \"%s\"", studio.getName())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Studio studio) {
        try {
            Studio saved = service.update(id, studio);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    String.format("Ошибка при сохранении жанра \"%s\" с ИД = %d", studio.getName(), id)
            );
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
