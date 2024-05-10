package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Comment;
import ds.dsinternshipcontrolsystem.entity.Commit;
import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import ds.dsinternshipcontrolsystem.entity.Role;
import ds.dsinternshipcontrolsystem.entity.Task;
import ds.dsinternshipcontrolsystem.entity.TaskFork;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> postgres = (PostgreSQLContainer)
            new PostgreSQLContainer<>("postgres").withReuse(true);

    @DynamicPropertySource
    private static void configurePostgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommitRepository commitRepository;
    @Autowired
    private TaskForkRepository taskForkRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private InternshipRepository internshipRepository;

    private final Internship internship = new Internship(null, "name", "desc",
            Timestamp.valueOf(LocalDateTime.now().plusDays(7)),
            Timestamp.valueOf(LocalDateTime.now()),
            InternshipStatus.REGISTRY,
            null,
            null);

    private final Lesson lesson = new Lesson(null, internship, "lesson1",
            "desc", null);

    private final Task task = new Task(null, lesson, "task1", "desc", "url1",
            "path1", null);


    private final User user = new User(null, "username3", "pass", "mail3", "fullName",
            "phone3", "telegram3", "s", "s", "s", "s",
            1, Role.ADMIN, null, null, null);

    private final TaskFork taskFork = new TaskFork(null, task, user, false, "url", null);

    private final Commit commit1 = new Commit(null, taskFork, "author",
            Timestamp.valueOf(LocalDateTime.now()),
            "url1", null);

    private final Commit commit2 = new Commit(null, taskFork, "author",
            Timestamp.valueOf(LocalDateTime.now()),
            "url2", null);

    @BeforeEach
    public void setUp() {
        userRepository.save(user);
        internshipRepository.save(internship);
        lessonRepository.save(lesson);
        taskRepository.save(task);
        taskForkRepository.save(taskFork);
        commitRepository.save(commit1);
        commitRepository.save(commit2);
    }

    @AfterEach
    public void tearDown() {
        commentRepository.deleteAll();
        commitRepository.deleteAll();
        taskForkRepository.deleteAll();
        taskRepository.deleteAll();
        lessonRepository.deleteAll();
        internshipRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findAllByCommitIdTest() {
        Comment comment1 = new Comment(null, user, commit1, "msg");
        Comment comment2 = new Comment(null, user, commit1, "msg");
        Comment comment3 = new Comment(null, user, commit2, "msg");

        List<Comment> expected = new ArrayList<>();
        expected.add(comment1);
        expected.add(comment2);

        commentRepository.saveAll(expected);
        commentRepository.save(comment3);

        List<Comment> actual = commentRepository.findAllByCommitId(commit1.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByUnexistingCommitIdTest(Integer commitId) {
        List<Comment> actual = commentRepository.findAllByCommitId(commitId);

        assertThat(actual).isEmpty();
    }
}
