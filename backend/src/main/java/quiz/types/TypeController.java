package quiz.types;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
@AllArgsConstructor
public class TypeController {

    private final TypeService service;

    @GetMapping
    public List<Type> getAll() {
        return this.service.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Type type) {
        try {
            Type saved = service.save(type);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    String.format("Ошибка при добавлении жанра \"%s\"", type.getName())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Type type) {
        try {
            Type saved = service.update(id, type);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    String.format("Ошибка при сохранении жанра \"%s\" с ИД = %d", type.getName(), id)
            );
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}
