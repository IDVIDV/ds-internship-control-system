package ds.dsinternshipcontrolsystem.repository.archive;

import ds.dsinternshipcontrolsystem.entity.archive.ArchiveUserInternship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveUserInternshipRepository extends JpaRepository<ArchiveUserInternship, Integer> {
}
