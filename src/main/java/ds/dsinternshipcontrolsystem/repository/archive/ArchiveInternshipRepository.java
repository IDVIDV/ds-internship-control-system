package ds.dsinternshipcontrolsystem.repository.archive;

import ds.dsinternshipcontrolsystem.entity.archive.ArchiveInternship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveInternshipRepository extends JpaRepository<ArchiveInternship, Integer> {
}
