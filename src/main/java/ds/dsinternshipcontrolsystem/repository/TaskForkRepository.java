package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.TaskFork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskForkRepository extends JpaRepository<TaskFork, Integer> {
    TaskFork findByUrl(String Url);
}
