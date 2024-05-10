package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import ds.dsinternshipcontrolsystem.entity.Task;
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
public class TaskRepositoryTest {
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
    private InternshipRepository internshipRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TaskRepository taskRepository;

    private final Internship internship1 = new Internship(null, "name", "desc",
            Timestamp.valueOf(LocalDateTime.now().plusDays(7)),
            Timestamp.valueOf(LocalDateTime.now()),
            InternshipStatus.REGISTRY,
            null,
            null);

    private final Internship internship2 = new Internship(null, "name", "desc",
            Timestamp.valueOf(LocalDateTime.now().plusDays(7)),
            Timestamp.valueOf(LocalDateTime.now()),
            InternshipStatus.REGISTRY,
            null,
            null);

    private final Lesson lesson1 = new Lesson(null, internship1, "lesson1",
            "desc", null);
    private final Lesson lesson2 = new Lesson(null, internship1, "lesson2",
            "desc", null);
    private final Lesson lesson3 = new Lesson(null, internship2, "lesson3",
            "desc", null);


    @BeforeEach
    public void setUp() {
        internshipRepository.save(internship1);
        internshipRepository.save(internship2);
        lessonRepository.save(lesson1);
        lessonRepository.save(lesson2);
        lessonRepository.save(lesson3);
    }

    @AfterEach
    public void tearDown() {
        taskRepository.deleteAll();
        lessonRepository.deleteAll();
        internshipRepository.deleteAll();
    }

    @Test
    void findAllByLessonIdTest() {
        Task task1 = new Task(null, lesson1, "task1", "desc", "url1",
                "path1", null);
        Task task2 = new Task(null, lesson1, "task2", "desc", "url2",
                "path2", null);
        Task task3 = new Task(null, lesson2, "task3", "desc", "url3",
                "path3", null);

        List<Task> expected = new ArrayList<>();
        expected.add(task1);
        expected.add(task2);

        taskRepository.saveAll(expected);
        taskRepository.save(task3);

        List<Task> actual = taskRepository.findAllByLessonId(lesson1.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByUnexistingLessonIdTest(Integer lessonId) {
        List<Task> actual = taskRepository.findAllByLessonId(lessonId);

        assertThat(actual).isEmpty();
    }

    @Test
    void findAllByLessonInternshipIdTest() {
        Task task1 = new Task(null, lesson1, "task1", "desc", "url1",
                "path1", null);
        Task task2 = new Task(null, lesson1, "task2", "desc", "url2",
                "path2", null);
        Task task3 = new Task(null, lesson2, "task3", "desc", "url3",
                "path3", null);
        Task task4 = new Task(null, lesson3, "task4", "desc", "url4",
                "path4", null);
        Task task5 = new Task(null, lesson3, "task5", "desc", "url5",
                "path5", null);

        List<Task> expected = new ArrayList<>();
        expected.add(task1);
        expected.add(task2);
        expected.add(task3);

        taskRepository.saveAll(expected);
        taskRepository.save(task4);
        taskRepository.save(task5);

        List<Task> actual = taskRepository.findAllByLessonInternshipId(internship1.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByLessonInternshipIdTest(Integer internshipId) {
        List<Task> actual = taskRepository.findAllByLessonInternshipId(internshipId);

        assertThat(actual).isEmpty();
    }
}
