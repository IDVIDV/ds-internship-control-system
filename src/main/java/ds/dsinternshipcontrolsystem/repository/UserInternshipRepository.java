package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.UserInternship;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import ds.dsinternshipcontrolsystem.repository.projection.UserOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserInternshipRepository extends JpaRepository<UserInternship, Integer> {
    List<UserOnly> findAllByInternshipIdAndStatus(Integer internshipId, UserInternshipStatus status);

    UserInternship findByInternshipIdAndUserId(Integer internshipId, Integer userId);

    List<UserInternship> findAllByInternshipIdAndUserIdIn(Integer internshipId, Collection<Integer> userIds);

}
