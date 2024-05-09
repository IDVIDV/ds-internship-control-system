package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.TaskFork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskForkRepository extends JpaRepository<TaskFork, Integer> {
    TaskFork findByUrl(String Url);
    List<TaskFork> findAllByTaskId(Integer taskId);
}
