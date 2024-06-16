package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Role;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.UserInternship;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import ds.dsinternshipcontrolsystem.repository.projection.UserOnly;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserInternshipRepositoryTest {
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
    private UserInternshipRepository userInternshipRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InternshipRepository internshipRepository;

    private final User user1 = new User(null, "username1", "pass",
            "mail1", "fullName", "phone1", "telegram1",
            "s", "s", "s", "s",
            1, Role.USER, null, null, null);

    private final User user2 = new User(null, "username2", "pass",
            "mail2", "fullName", "phone2", "telegram2",
            "s", "s", "s", "s",
            1, Role.USER, null, null, null);
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

    @BeforeEach
    public void setUp() {
        userRepository.save(user1);
        userRepository.save(user2);
        internshipRepository.save(internship1);
        internshipRepository.save(internship2);
    }

    @AfterEach
    public void tearDown() {
        userInternshipRepository.deleteAll();
        internshipRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findByInternshipIdAndUserIdTest() {
        UserInternship userInternship = new UserInternship(null, internship1,
                user1, UserInternshipStatus.JOINED);
        userInternshipRepository.save(userInternship);

        UserInternship actual = userInternshipRepository
                .findByInternshipIdAndUserId(internship1.getId(), user1.getId());

        assertThat(actual).isEqualTo(userInternship);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findByUnexistingInternshipIdAndUserIdTest(Integer internshipId) {
        UserInternship actual = userInternshipRepository
                .findByInternshipIdAndUserId(internshipId, user1.getId());

        assertThat(actual).isNull();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findByInternshipIdAndUnexistingUserIdTest(Integer userId) {
        UserInternship actual = userInternshipRepository
                .findByInternshipIdAndUserId(internship1.getId(), userId);

        assertThat(actual).isNull();
    }

    @Test
    void findAllByInternshipIdAndStatusTest() {
        UserInternship userInternship = new UserInternship(null, internship1,
                user1, UserInternshipStatus.JOINED);
        userInternshipRepository.save(userInternship);

        List<User> expected = new ArrayList<>();
        expected.add(user1);

        List<User> actual = userInternshipRepository
                .findAllByInternshipIdAndStatus(internship1.getId(), UserInternshipStatus.JOINED)
                .stream().map(UserOnly::getUser).collect(Collectors.toList());

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByUnexistingInternshipIdAndStatusTest(Integer internshipId) {
        List<User> actual = userInternshipRepository
                .findAllByInternshipIdAndStatus(internshipId, UserInternshipStatus.JOINED)
                .stream().map(UserOnly::getUser).collect(Collectors.toList());

        assertThat(actual).isEmpty();
    }
}
