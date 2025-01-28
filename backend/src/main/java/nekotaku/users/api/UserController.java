package nekotaku.users.api;

import org.springframework.web.bind.annotation.*;
import nekotaku.users.api.dto.UserCreateDTO;
import nekotaku.users.api.dto.UserGetDTO;
import nekotaku.users.api.dto.UserUpdateDTO;
import nekotaku.users.service.UserService;

import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping()
  public List<UserGetDTO> getAll(){
    return this.userService.findAll();
  }

  @GetMapping("{id}")
  public UserGetDTO getById(@PathVariable @NotNull Long id){
    return this.userService.getById(id);
  }

  @PostMapping("/registration")
  public Long create(@RequestBody @NotNull UserCreateDTO userDTO){
    return this.userService.add(userDTO);
  }

  @PutMapping("{id}")
  public Long update(@RequestBody @NotNull UserUpdateDTO userDTO) throws IOException {
    return this.userService.update(userDTO);
  }

  @DeleteMapping("{id}")
  public Long delete(@PathVariable @NotNull Long id, @RequestBody @NotNull UserCreateDTO userDTO){
    userDTO.setId(id);
    return this.userService.delete(userDTO);
  }

}
