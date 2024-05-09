package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    List<User> findAllByRoleId(Integer roleId);

    List<User> findAllByUserInternshipsInternshipIdAndUserInternshipsStatus(
            Integer internshipId,
            UserInternshipStatus status);
}
