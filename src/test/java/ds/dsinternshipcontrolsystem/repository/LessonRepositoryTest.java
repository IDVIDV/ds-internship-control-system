package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Lesson;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
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
public class LessonRepositoryTest {
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
    private LessonRepository lessonRepository;
    @Autowired
    private InternshipRepository internshipRepository;

    @Test
    void findAllByInternshipIdTest() {
        Internship internship1 = new Internship(null, "name", "desc",
                Timestamp.valueOf(LocalDateTime.now().plusDays(7)),
                Timestamp.valueOf(LocalDateTime.now()),
                InternshipStatus.REGISTRY,
                null,
                null);

        Internship internship2 = new Internship(null, "name", "desc",
                Timestamp.valueOf(LocalDateTime.now().plusDays(7)),
                Timestamp.valueOf(LocalDateTime.now()),
                InternshipStatus.REGISTRY,
                null,
                null);

        internshipRepository.save(internship1);
        internshipRepository.save(internship2);

        Lesson lesson1 = new Lesson(null, internship1, "lesson1", "desc", null);
        Lesson lesson2 = new Lesson(null, internship1, "lesson2", "desc", null);
        Lesson lesson3 = new Lesson(null, internship2, "lesson3", "desc", null);

        List<Lesson> expected = new ArrayList<>();
        expected.add(lesson1);
        expected.add(lesson2);

        lessonRepository.saveAll(expected);
        lessonRepository.save(lesson3);

        List<Lesson> actual = lessonRepository.findAllByInternshipId(internship1.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByUnexistingInternshipIdTest(Integer internshipId) {
        List<Lesson> actual = lessonRepository.findAllByInternshipId(internshipId);

        assertThat(actual).isEmpty();
    }
}
