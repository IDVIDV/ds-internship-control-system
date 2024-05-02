package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
