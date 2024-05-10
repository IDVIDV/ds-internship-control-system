package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.TaskFork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TaskForkRepository extends JpaRepository<TaskFork, Integer> {
    TaskFork findByUrl(String Url);

    List<TaskFork> findAllByTaskId(Integer taskId);

    List<TaskFork> findAllByUserId(Integer userId);

    List<TaskFork> findAllByUserIdInAndTaskLessonInternshipId(Collection<Integer> userId, Integer internshipId);
}
