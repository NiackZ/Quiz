package nekotaku.links;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/links")
@AllArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping
    public List<Link> getAll() {
        return linkService.findAll();
    }

}
