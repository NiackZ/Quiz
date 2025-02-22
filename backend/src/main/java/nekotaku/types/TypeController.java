package nekotaku.types;

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
    public ResponseEntity<Type> create(@RequestBody Type type) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Type> update(@PathVariable Long id, @RequestBody Type type) {
        return ResponseEntity.ok(service.update(id, type));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
