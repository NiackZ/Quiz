package nekotaku.users.api;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import nekotaku.users.api.dto.UserCreateDTO;
import nekotaku.users.api.dto.UserGetDTO;
import nekotaku.users.api.dto.UserUpdateDTO;
import nekotaku.users.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public List<UserGetDTO> getAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public UserGetDTO getById(@PathVariable @NotNull Long id) {
    return userService.getById(id);
  }

  @PostMapping("/registration")
  public Long create(@RequestBody @NotNull UserCreateDTO userDTO) {
    return userService.add(userDTO);
  }

  @PutMapping("/{id}")
  public Long update(@RequestBody @NotNull UserUpdateDTO userDTO) throws IOException {
    return userService.update(userDTO);
  }

  @DeleteMapping("/{id}")
  public Long delete(@PathVariable @NotNull Long id, @RequestBody @NotNull UserCreateDTO userDTO) {
    userDTO.setId(id);
    return userService.delete(userDTO);
  }
}
