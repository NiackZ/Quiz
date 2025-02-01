package nekotaku.users.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import nekotaku.auth.api.dto.RegistrationUserDto;
import nekotaku.roles.services.RoleService;
import nekotaku.users.api.dto.UserCreateDTO;
import nekotaku.users.api.dto.UserGetDTO;
import nekotaku.users.api.dto.UserUpdateDTO;
import nekotaku.users.model.User;
import nekotaku.users.repository.IUserRepository;
import nekotaku.utils.Utils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
  private final IUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleService roleService;

  private UserGetDTO userToUserGetDTO(User user) {
      return new UserGetDTO(user);
  }

  public User findById(@NotNull Long id){
    return this.userRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(String.format("Пользователь с ИД %d не найден", id))
    );
  }

  public User findByUserName(@NotNull String username) {
    return this.userRepository.findByUsername(username);
  }

  public UserGetDTO getById(@NotNull Long id) {
    return userToUserGetDTO(findById(id));
  }

  public UserGetDTO getByUserName(@NotNull String username) {
    return userToUserGetDTO(findByUserName(username));
  }

  public List<UserGetDTO> findAll() {
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
        this.userRepository.updateAvatar(Utils.setPoster(userData.getAvatar(), user.getId(), user.getAvatarURL(), "/images/user/avatar/"), user.getId());
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
      throw new EntityNotFoundException(String.format("Пользователь '%s' не найден", username));
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
