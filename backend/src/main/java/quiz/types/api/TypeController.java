package quiz.types.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quiz.types.model.Type;
import quiz.types.service.TypeService;

import java.util.List;

@RestController
@RequestMapping("/types")
@AllArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping
    public List<Type> getAll() {
        return this.typeService.findAll();
    }
}
