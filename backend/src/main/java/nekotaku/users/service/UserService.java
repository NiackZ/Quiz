package nekotaku.users.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import nekotaku.auth.api.dto.RegistrationUserDto;
import nekotaku.roles.services.RoleService;
import nekotaku.users.api.dto.UserCreateDTO;
import nekotaku.users.api.dto.UserGetDTO;
import nekotaku.users.api.dto.UserUpdateDTO;
import nekotaku.users.model.User;
import nekotaku.users.repository.IUserRepository;
import nekotaku.utils.Utils;
import nekotaku.utils.model.UpdateImage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;
  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private UserGetDTO userToUserGetDTO(User user){
    UserGetDTO userGetDTO = new UserGetDTO(user);
//    userGetDTO.setQuizzes(user.getQuizzes());
    return userGetDTO;
  }

  public User findById(@NotNull Long id){
    return this.userRepository.findById(id).orElseThrow(
        () -> new RuntimeException(String.format("Пользователь с ИД %d не найден", id))
    );
  }

  public User findByUserName(@NotNull String username) {
    return this.userRepository.findByUsername(username);
  }

  public UserGetDTO getById(@NotNull Long id){
    return userToUserGetDTO(findById(id));
  }

  public UserGetDTO getByUserName(@NotNull String username){
    return userToUserGetDTO(findByUserName(username));
  }

  public List<UserGetDTO> findAll(){
    return this.userRepository.findAll().stream().map(this::userToUserGetDTO).toList();
  }

  public Long add(@Valid @NotNull UserCreateDTO userData) {
    User user = userData.getId() == null ? new User() : findById(userData.getId());

    user.setUsername(userData.getUsername());
    user.setEmail(userData.getEmail());
    user.setPassword(userData.getPassword());
    user.setActive(userData.isActive());

    return this.userRepository.save(user).getId();
  }

  public Long update(@Valid @NotNull UserUpdateDTO userData) throws IOException {
    String prevPoserPath = null;
    try {
      User user = findById(userData.getId());
      if (userData.getAvatar() != null) {
        UpdateImage image = Utils.setPoster(userData.getAvatar(), user.getId(), user.getAvatarURL(), "/images/user/avatar/");
        prevPoserPath = image.getURL();
        this.userRepository.updateAvatar(prevPoserPath, image.getId());
      }
    }
    catch (Exception e){
      if (prevPoserPath != null) {
        Utils.deletePoster(prevPoserPath);
      }
      throw e;
    }
    return null;
  }

  public Long delete(@Valid @NotNull UserCreateDTO userData) {
    userData.setActive(false);
    return add(userData);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = findByUserName(username);
    if (user == null) {
      throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
    }
    return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
    );
  }

  public User createNewUser(RegistrationUserDto registrationUserDto) {
    User user = new User();
    user.setUsername(registrationUserDto.getUsername());
    user.setEmail(registrationUserDto.getEmail());
    user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
    user.setRoles(List.of(roleService.getUserRole()));
    return userRepository.save(user);
  }
}
