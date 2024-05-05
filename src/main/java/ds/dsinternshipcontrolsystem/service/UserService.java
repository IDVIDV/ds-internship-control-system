package ds.dsinternshipcontrolsystem.service;

import ds.dsinternshipcontrolsystem.dto.RegisterUser;
import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Role;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.UserInternship;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import ds.dsinternshipcontrolsystem.exception.AlreadyJoinedException;
import ds.dsinternshipcontrolsystem.exception.InternshipRegistryClosedException;
import ds.dsinternshipcontrolsystem.repository.InternshipRepository;
import ds.dsinternshipcontrolsystem.repository.UserInternshipRepository;
import ds.dsinternshipcontrolsystem.repository.UserRepository;
import ds.dsinternshipcontrolsystem.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final InternshipRepository internshipRepository;
    private final UserInternshipRepository userInternshipRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean registerUser(RegisterUser registerUser) {
        User foundUser = userRepository.findByUsername(registerUser.getUsername());

        if (foundUser != null) {
            return false;
        }

        User user = userMapper.toUser(registerUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(new Role(2, "USER"));
        userRepository.save(user);

        return true;
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
                .findUserInternshipByInternshipIdAndUserId(user.getId(), internshipId);
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
}

