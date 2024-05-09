package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.MessageDto;
import ds.dsinternshipcontrolsystem.dto.Performance;
import ds.dsinternshipcontrolsystem.dto.RegisterUser;
import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Role;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.UserInternship;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import ds.dsinternshipcontrolsystem.exception.AlreadyJoinedException;
import ds.dsinternshipcontrolsystem.exception.InternshipRegistryClosedException;
import ds.dsinternshipcontrolsystem.exception.UserAlreadyRegisteredException;
import ds.dsinternshipcontrolsystem.mapper.TaskForkMapper;
import ds.dsinternshipcontrolsystem.mapper.UserMapper;
import ds.dsinternshipcontrolsystem.repository.InternshipRepository;
import ds.dsinternshipcontrolsystem.repository.TaskForkRepository;
import ds.dsinternshipcontrolsystem.repository.UserInternshipRepository;
import ds.dsinternshipcontrolsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final InternshipRepository internshipRepository;
    private final UserInternshipRepository userInternshipRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final MessageService messageService;
    private final TaskForkRepository taskForkRepository;
    private final TaskForkMapper taskForkMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public void registerUser(RegisterUser registerUser) {
        User foundUser = userRepository.findByUsername(registerUser.getUsername());

        if (foundUser != null) {
            throw new UserAlreadyRegisteredException("Username is taken");
        }

        User user = userMapper.toUser(registerUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    public void signInInternship(Integer internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElse(null);

        if (internship == null) {
            throw new EntityNotFoundException("Internship with given id does not exist");
        }

        if (!internship.getStatus().equals(InternshipStatus.REGISTRY)) {
            throw new InternshipRegistryClosedException("Internship is not opened for registry");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            throw new EntityNotFoundException("User not authenticated");
        }

        UserInternship userInternship = userInternshipRepository
                .findByInternshipIdAndUserId(user.getId(), internshipId);
        UserInternship userInternshipToSave;

        if (userInternship != null) {
            if (userInternship.getStatus().equals(UserInternshipStatus.JOINED)) {
                throw new AlreadyJoinedException("Already joined");
            }

            userInternshipToSave = userInternship;
            userInternshipToSave.setStatus(UserInternshipStatus.JOINED);
        } else {
            userInternshipToSave = new UserInternship();
            userInternshipToSave.setInternship(internship);
            userInternshipToSave.setUser(user);
            userInternshipToSave.setStatus(UserInternshipStatus.JOINED);
        }

        userInternshipRepository.save(userInternshipToSave);
    }

    public List<Performance> getPerformance() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            throw new EntityNotFoundException("User not authenticated");
        }

        return taskForkMapper.toPerformanceList(taskForkRepository.findAllByUserId(user.getId()));
    }

    public List<MessageDto> getMessages() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user == null) {
            throw new EntityNotFoundException("User not authenticated");
        }

        return messageService.getMessagesByReceiverId(user.getId());
    }
}

