package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
