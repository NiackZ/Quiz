package nekotaku.marks;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/marks")
@AllArgsConstructor
public class MarkController {

    private final MarkService markService;

    @GetMapping
    public List<Mark> getAll() {
        return this.markService.findAll();
    }

}
