package ds.dsinternshipcontrolsystem.repository;

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
public class CommitRepositoryTest {
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
    private CommitRepository commitRepository;
    @Autowired
    private TaskForkRepository taskForkRepository;
    @Autowired
    private UserRepository userRepository;
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

    private final Lesson lesson1 = new Lesson(null, internship, "lesson1",
            "desc", null);
    private final Lesson lesson2 = new Lesson(null, internship, "lesson2",
            "desc", null);

    private final Task task1 = new Task(null, lesson1, "task1", "desc", "url1",
            "path1", null);

    private final Task task2 = new Task(null, lesson1, "task2", "desc", "url2",
            "path2", null);

    private final Task task3 = new Task(null, lesson2, "task3", "desc", "url3",
            "path3", null);

    private final User user = new User(null, "username1", "pass", "mail1", "fullName",
            "phone1", "telegram1", "s", "s", "s", "s",
            1, Role.ADMIN, null, null, null);

    private final TaskFork taskFork1 = new TaskFork(null, task1, user, false, "url1", null);
    private final TaskFork taskFork2 = new TaskFork(null, task1, user, true, "url2", null);
    private final TaskFork taskFork3 = new TaskFork(null, task2, user, false, "url3", null);
    private final TaskFork taskFork4 = new TaskFork(null, task3, user, true, "url4", null);

    private final Commit commit1 = new Commit(null, taskFork1, "author",
            Timestamp.valueOf(LocalDateTime.now()),
            "url1", null);

    private final Commit commit2 = new Commit(null, taskFork1, "author",
            Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
            "url2", null);

    private final Commit commit3 = new Commit(null, taskFork2, "author",
            Timestamp.valueOf(LocalDateTime.now()),
            "url3", null);

    private final Commit commit4 = new Commit(null, taskFork2, "author",
            Timestamp.valueOf(LocalDateTime.now().plusDays(1)),
            "url4", null);

    private final Commit commit5 = new Commit(null, taskFork3, "author",
            Timestamp.valueOf(LocalDateTime.now()),
            "url5", null);

    private final Commit commit6 = new Commit(null, taskFork4, "author",
            Timestamp.valueOf(LocalDateTime.now()),
            "url6", null);

    @BeforeEach
    public void setUp() {
        userRepository.save(user);
        internshipRepository.save(internship);
        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);
        taskForkRepository.save(taskFork1);
        taskForkRepository.save(taskFork2);
        taskForkRepository.save(taskFork3);
        taskForkRepository.save(taskFork4);
        commitRepository.save(commit1);
        commitRepository.save(commit2);
        commitRepository.save(commit3);
        commitRepository.save(commit4);
        commitRepository.save(commit5);
        commitRepository.save(commit6);
    }

    @AfterEach
    public void tearDown() {
        commitRepository.deleteAll();
        taskForkRepository.deleteAll();
        taskRepository.deleteAll();
        lessonRepository.deleteAll();
        internshipRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByIdAndTaskForkAcceptedFalseTest() {
        Commit actual = commitRepository.findByIdAndTaskForkAccepted(commit1.getId(), false);

        assertThat(actual).isEqualTo(commit1);
    }

    @Test
    void findByIdAndTaskForkAcceptedTrueTest() {
        Commit actual = commitRepository.findByIdAndTaskForkAccepted(commit3.getId(), false);

        assertThat(actual).isNull();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findByUnexistingIdAndTaskForkAcceptedTest(Integer commitId) {
        Commit actual = commitRepository.findByIdAndTaskForkAccepted(commitId, false);

        assertThat(actual).isNull();
    }

    @Test
    void findAllLatestUncheckedCommitsByLessonIdTest() {
        List<Commit> expected = new ArrayList<>();
        expected.add(commit2);
        expected.add(commit5);

        List<Commit> actual = commitRepository.findAllLatestUncheckedCommitsByLessonId(lesson1.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1})
    void findAllLatestUncheckedCommitsByUnexistingLessonIdTest(Integer lessonId) {
        List<Commit> actual = commitRepository.findAllLatestUncheckedCommitsByLessonId(lessonId);

        assertThat(actual).isEmpty();
    }

    @Test
    void findAllLatestUncheckedCommitsByLessonIdWithAllCheckedTest() {
        List<Commit> actual = commitRepository.findAllLatestUncheckedCommitsByLessonId(lesson2.getId());

        assertThat(actual).isEmpty();
    }

}
