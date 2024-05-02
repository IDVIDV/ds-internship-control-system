package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
