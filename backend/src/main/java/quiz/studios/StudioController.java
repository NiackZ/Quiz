package quiz.studios;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
