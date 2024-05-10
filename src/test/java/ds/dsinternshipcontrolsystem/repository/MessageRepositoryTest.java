package ds.dsinternshipcontrolsystem.repository;

import ds.dsinternshipcontrolsystem.entity.Message;
import ds.dsinternshipcontrolsystem.entity.Role;
import ds.dsinternshipcontrolsystem.entity.User;
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
public class MessageRepositoryTest {
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
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllByReceiverIdTest() {
        User user1 = new User(null, "username1", "pass", "mail1", "fullName",
                "phone1", "telegram1", "s", "s", "s", "s",
                1, Role.USER, null, null, null);
        User user2 = new User(null, "username2", "pass", "mail2", "fullName",
                "phone2", "telegram2", "s", "s", "s", "s",
                1, Role.USER, null, null, null);

        userRepository.save(user1);
        userRepository.save(user2);

        Message message1 = new Message(null, Timestamp.valueOf(LocalDateTime.now()),
                "message1", null, user1);
        Message message2 = new Message(null, Timestamp.valueOf(LocalDateTime.now()),
                "message2", null, user1);
        Message message3 = new Message(null, Timestamp.valueOf(LocalDateTime.now()),
                "message3", null, user2);

        List<Message> expected = new ArrayList<>();
        expected.add(message1);
        expected.add(message2);

        messageRepository.saveAll(expected);
        messageRepository.save(message3);

        List<Message> actual = messageRepository.findAllByReceiverId(user1.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(ints = {-1})
    void findAllByUnexistingReceiverIdTest(Integer receiverId) {
        List<Message> actual = messageRepository.findAllByReceiverId(receiverId);

        assertThat(actual).isEmpty();
    }
}
