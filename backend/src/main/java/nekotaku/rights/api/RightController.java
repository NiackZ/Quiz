package nekotaku.rights.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import nekotaku.rights.model.Right;
import nekotaku.rights.model.dto.CheckRightsDTO;
import nekotaku.rights.services.RightService;

import java.util.List;

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
  public boolean checkRights(@RequestBody CheckRightsDTO requestData) {
    Long userId = requestData.getUserId();
    List<String> rights = requestData.getRights();
    return this.rightService.checkRights(userId, rights);
  }

}
