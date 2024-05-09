package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommitRepository extends JpaRepository<Commit, Integer> {
    @Query(value =
            "SELECT c1.commit_id, c1.task_fork_id, c1.author, c1.commit_date, c1.url " +
            "FROM lesson AS l " +
            "JOIN task AS tk ON tk.lesson_id = l.lesson_id " +
            "JOIN task_fork AS tf ON tf.task_id = tk.task_id " +
            "JOIN \"commit\" AS c1 ON tf.task_fork_id = c1.task_fork_id " +
            "JOIN (SELECT task_fork_id, MAX(commit_date) AS commit_date " +
            "      FROM \"commit\" AS c2\n" +
            "      GROUP BY task_fork_id\n" +
            "     ) AS c2 " +
            "ON c1.task_fork_id = c2.task_fork_id AND c1.commit_date = c2.commit_date " +
            "WHERE l.lesson_id = :lessonId AND tf.accepted = false",
            nativeQuery = true)
    List<Commit> findAllLatestUncheckedCommitsByLessonId(@Param("lessonId") Integer lessonId);

    Commit findByIdAndTaskForkAccepted(Integer commitId, Boolean accepted);
}