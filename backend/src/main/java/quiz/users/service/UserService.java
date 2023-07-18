package quiz.users.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quiz.auth.api.dto.RegistrationUserDto;
import quiz.roles.services.RoleService;
import quiz.users.api.dto.UserCreateDTO;
import quiz.users.api.dto.UserGetDTO;
import quiz.users.model.User;
import quiz.users.repository.IUserRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
  private final IUserRepository userRepository;
  private PasswordEncoder passwordEncoder;
  private RoleService roleService;

  public UserService(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private UserGetDTO userToUserGetDTO(User user){
    UserGetDTO userGetDTO = new UserGetDTO(user);
    userGetDTO.setQuizzes(user.getQuizzes());
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

  public List<UserGetDTO> findAll(){
    return this.userRepository.findAll().stream().map(this::userToUserGetDTO).toList();
  }

  public Long add(@Valid @NotNull UserCreateDTO userData) {
    User user = userData.getId() == null ? new User() : findById(userData.getId());

    user.setUsername(userData.getUsername());
    user.setEmail(userData.getEmail());
    user.setPassword(userData.getPassword());
    user.setDeleted(userData.isDeleted());

    return this.userRepository.save(user).getId();
  }

  public Long update(@Valid @NotNull UserCreateDTO userData){
    return add(userData);
  }

  public Long delete(@Valid @NotNull UserCreateDTO userData){
    userData.setDeleted(true);
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
