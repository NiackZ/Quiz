package quiz.status;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
