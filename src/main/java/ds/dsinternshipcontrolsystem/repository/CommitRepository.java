package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<Commit, Integer> {
}
