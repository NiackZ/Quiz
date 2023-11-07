package quiz.rights.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import quiz.rights.model.Right;
import quiz.rights.services.RightService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rights")
@AllArgsConstructor
public class RightController {

  private final RightService rightService;

  @GetMapping()
  public List<Right> getAll(){
    return this.rightService.findAll();
  }

  @PostMapping("check-rights")
  public boolean checkRights(@RequestBody Map<String, Object> requestData) {
    Long userId = Long.valueOf(requestData.get("userId").toString());
    List<String> rights = (List<String>) requestData.get("rights");
    return this.rightService.checkRights(userId, rights);
  }

}
