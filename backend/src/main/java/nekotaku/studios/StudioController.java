package nekotaku.studios;

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
    public ResponseEntity<Studio> create(@RequestBody Studio studio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(studio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Studio> update(@PathVariable Long id, @RequestBody Studio studio) {
        return ResponseEntity.ok(service.update(id, studio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
