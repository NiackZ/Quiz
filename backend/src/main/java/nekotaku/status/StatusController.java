package nekotaku.status;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
@AllArgsConstructor
public class StatusController {

    private final StatusService service;

    @GetMapping
    public List<Status> getAll() {
        return this.service.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Status status) {
        try {
            Status saved = service.save(status);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    String.format("Ошибка при добавлении жанра \"%s\"", status.getName())
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Status status) {
        try {
            Status saved = service.update(id, status);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    String.format("Ошибка при сохранении жанра \"%s\" с ИД = %d", status.getName(), id)
            );
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}
