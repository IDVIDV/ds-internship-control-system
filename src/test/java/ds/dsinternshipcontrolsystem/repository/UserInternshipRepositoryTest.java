package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Role;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.UserInternship;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserInternshipRepositoryTest {
    @Container
    private static final PostgreSQLContainer<?> postgres = (PostgreSQLContainer)
            new PostgreSQLContainer("postgres").withReuse(true);

    static {
        postgres.start();
    }

    @DynamicPropertySource
    private static void configurePostgreSQLProperties(DynamicPropertyRegistry registry) {
        postgres.start();
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

    private final User user = new User(null, "user", "pass", "mail", "fullName",
            "phone", "telegram", "s", "s", "s", "s",
            1, new Role(1, "USER"), null, null);
    private final Internship internship = new Internship(null, "name", "desc",
            Timestamp.valueOf(LocalDateTime.now().plusDays(7)),
            Timestamp.valueOf(LocalDateTime.now()),
            InternshipStatus.REGISTRY,
            null,
            null);

    @BeforeEach
    public void setUp() {
        userRepository.save(user);
        internshipRepository.save(internship);
    }

    @AfterEach
    public void tearDown() {
        userInternshipRepository.deleteAll();
        internshipRepository.deleteAll();
        userRepository.deleteById(user.getId());
    }

    @Test
    void getUserInternshipByInternshipIdAndUserid() {
        Internship internship1 = new Internship();
        internship1.setId(1);
        UserInternship userInternship = new UserInternship(null, internship1,
                user, UserInternshipStatus.JOINED);
        userInternshipRepository.save(userInternship);

        assertThat(userInternshipRepository
                .findUserInternshipByInternshipIdAndUserId(internship.getId(), user.getId()))
                .isEqualTo(userInternship);
    }
}
