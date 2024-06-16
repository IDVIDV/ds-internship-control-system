package ds.dsinternshipcontrolsystem.repository;

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
import org.junit.jupiter.params.provider.EmptySource;
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
public class TaskForkRepositoryTest {
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

    private final Lesson lesson = new Lesson(null, internship, "lesson1",
            "desc", null);

    private final Task task1 = new Task(null, lesson, "task1", "desc", "url1",
            "path1", null);

    private final Task task2 = new Task(null, lesson, "task2", "desc", "url2",
            "path2", null);

    private final User user1 = new User(null, "username1", "pass", "mail1", "fullName",
            "phone1", "telegram1", "s", "s", "s", "s",
            1, Role.ADMIN, null, null, null);

    private final User user2 = new User(null, "username2", "pass", "mail2", "fullName",
            "phone2", "telegram2", "s", "s", "s", "s",
            1, Role.ADMIN, null, null, null);

    @BeforeEach
    public void setUp() {
        userRepository.save(user1);
        userRepository.save(user2);
        internshipRepository.save(internship);
        lessonRepository.save(lesson);
        taskRepository.save(task1);
        taskRepository.save(task2);
    }

    @AfterEach
    public void tearDown() {
        taskForkRepository.deleteAll();
        taskRepository.deleteAll();
        lessonRepository.deleteAll();
        internshipRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByUrlTest() {
        TaskFork taskFork1 = new TaskFork(null, task1, user1,
                false, "url1", null);
        TaskFork taskFork2 = new TaskFork(null, task1, user2,
                false, "url2", null);

        taskForkRepository.save(taskFork1);
        taskForkRepository.save(taskFork2);

        TaskFork actual = taskForkRepository.findByUrl(taskFork1.getUrl());

        assertThat(actual).isEqualTo(taskFork1);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    void findByUnexistingUrlTest(String url) {
        TaskFork actual = taskForkRepository.findByUrl(url);

        assertThat(actual).isNull();
    }

    @Test
    void findAllByTaskIdTest() {
        TaskFork taskFork1 = new TaskFork(null, task1, user1,
                false, "url1", null);
        TaskFork taskFork2 = new TaskFork(null, task1, user2,
                false, "url2", null);
        TaskFork taskFork3 = new TaskFork(null, task2, user1,
                false, "url3", null);

        List<TaskFork> expected = new ArrayList<>();
        expected.add(taskFork1);
        expected.add(taskFork2);

        taskForkRepository.saveAll(expected);
        taskForkRepository.save(taskFork3);

        List<TaskFork> actual = taskForkRepository.findAllByTaskId(task1.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByUnexistingTaskIdTest(Integer taskId) {
        List<TaskFork> actual = taskForkRepository.findAllByTaskId(taskId);

        assertThat(actual).isEmpty();
    }

    @Test
    void findAllByUserIdTest() {
        TaskFork taskFork1 = new TaskFork(null, task1, user1,
                false, "url1", null);
        TaskFork taskFork2 = new TaskFork(null, task1, user2,
                false, "url2", null);
        TaskFork taskFork3 = new TaskFork(null, task2, user1,
                false, "url3", null);

        List<TaskFork> expected = new ArrayList<>();
        expected.add(taskFork1);
        expected.add(taskFork3);

        taskForkRepository.saveAll(expected);
        taskForkRepository.save(taskFork2);

        List<TaskFork> actual = taskForkRepository.findAllByUserId(user1.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByUnexistingUserIdTest(Integer userId) {
        List<TaskFork> actual = taskForkRepository.findAllByUserId(userId);

        assertThat(actual).isEmpty();
    }

}
