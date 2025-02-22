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
    public ResponseEntity<Status> create(@RequestBody Status status) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> update(@PathVariable Long id, @RequestBody Status status) {
        return ResponseEntity.ok(service.update(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
