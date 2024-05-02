package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
