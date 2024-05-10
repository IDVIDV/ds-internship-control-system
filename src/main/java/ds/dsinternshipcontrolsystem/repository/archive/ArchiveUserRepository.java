package ds.dsinternshipcontrolsystem.repository.archive;

import ds.dsinternshipcontrolsystem.entity.archive.ArchiveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveUserRepository extends JpaRepository<ArchiveUser, Integer> {
}
