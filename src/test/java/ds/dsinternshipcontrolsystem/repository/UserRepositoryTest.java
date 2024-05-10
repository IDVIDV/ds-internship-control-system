package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Internship;
import ds.dsinternshipcontrolsystem.entity.Role;
import ds.dsinternshipcontrolsystem.entity.User;
import ds.dsinternshipcontrolsystem.entity.UserInternship;
import ds.dsinternshipcontrolsystem.entity.status.InternshipStatus;
import ds.dsinternshipcontrolsystem.entity.status.UserInternshipStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
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
    private UserRepository userRepository;
    @Autowired
    private UserInternshipRepository userInternshipRepository;
    @Autowired
    private InternshipRepository internshipRepository;

    private static Stream<Arguments> getUserLists() {
        User user1 = new User(null, "username1", "pass", "mail1", "fullName",
                "phone1", "telegram1", "s", "s", "s", "s",
                1, Role.USER, null, null, null);

        User user2 = new User(null, "username2", "pass", "mail2", "fullName",
                "phone2", "telegram2", "s", "s", "s", "s",
                1, Role.USER, null, null, null);

        User user3 = new User(null, "username3", "pass", "mail3", "fullName",
                "phone3", "telegram3", "s", "s", "s", "s",
                1, Role.ADMIN, null, null, null);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        List<User> admins = new ArrayList<>();
        admins.add(user3);

        return Stream.of(
                Arguments.of(
                        users, Role.USER.getId()
                ),
                Arguments.of(
                        admins, Role.ADMIN.getId()
                )
        );
    }

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void findByUsernameTest() {
        User user = new User(null, "username1", "pass", "mail1", "fullName",
                "phone1", "telegram1", "s", "s", "s", "s",
                1, Role.USER, null, null, null);

        userRepository.save(user);

        User actualUser = userRepository.findByUsername(user.getUsername());

        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    void findByUnexistingUsernameTest() {
        User actualUser = userRepository.findByUsername("");

        assertThat(actualUser).isNull();
    }

    @ParameterizedTest
    @MethodSource("getUserLists")
    void findAllByRoleIdTest(List<User> users, Integer roleId) {
        userRepository.saveAll(users);

        List<User> actualUsers = userRepository.findAllByRoleId(roleId);

        assertThat(actualUsers).isEqualTo(users);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByUnexistingRoleIdTest(Integer roleId) {
        List<User> actualUsers = userRepository.findAllByRoleId(roleId);

        assertThat(actualUsers).isEmpty();
    }

    @Test
    void findAllByUserInternshipsInternshipIdAndUserInternshipsStatusTest() {
        User user1 = new User(null, "username1", "pass", "mail1", "fullName",
                "phone1", "telegram1", "s", "s", "s", "s",
                1, Role.USER, null, null, null);

        User user2 = new User(null, "username2", "pass", "mail2", "fullName",
                "phone2", "telegram2", "s", "s", "s", "s",
                1, Role.USER, null, null, null);

        User user3 = new User(null, "username3", "pass", "mail3", "fullName",
                "phone3", "telegram3", "s", "s", "s", "s",
                1, Role.ADMIN, null, null, null);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

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

        UserInternship userInternship1 = new UserInternship(null, internship1,
                user1, UserInternshipStatus.JOINED);
        UserInternship userInternship2 = new UserInternship(null, internship1,
                user2, UserInternshipStatus.JOINED);
        UserInternship userInternship3 = new UserInternship(null, internship2,
                user2, UserInternshipStatus.JOINED);
        UserInternship userInternship4 = new UserInternship(null, internship2,
                user1, UserInternshipStatus.JOINED);
        UserInternship userInternship5 = new UserInternship(null, internship2,
                user3, UserInternshipStatus.JOINED);

        userInternshipRepository.save(userInternship1);
        userInternshipRepository.save(userInternship2);
        userInternshipRepository.save(userInternship3);
        userInternshipRepository.save(userInternship4);
        userInternshipRepository.save(userInternship5);

        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);

        List<User> actual = userRepository.findAllByUserInternshipsInternshipIdAndUserInternshipsStatus(
                internship1.getId(),
                UserInternshipStatus.JOINED
        );

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByUserInternshipsInternshipIdAndUserInternshipsStatusTest(Integer internshipId) {

        List<User> actual = userRepository.findAllByUserInternshipsInternshipIdAndUserInternshipsStatus(
                internshipId,
                UserInternshipStatus.JOINED
        );

        assertThat(actual).isEmpty();
    }
}
