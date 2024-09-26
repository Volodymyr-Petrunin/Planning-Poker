package planing.poker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.Role;
import planing.poker.domain.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql(value = "classpath:script/user_repository.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
@DisplayName("User Repository Tests")
class UserRepositoryTest {
    private static final String EXPECTED_NAME = "Expected Name";

    private static final String EXPECTED_LASTNAME = "Expected Lastname";

    private static final String EXPECTED_NICKNAME = "Expected Nickname";

    private static final String EXPECTED_EMAIL = "expected@email.gg";

    private static final String EXPECTED_PASS = "Expected pass";

    private static final Role EXPECTED_ROLE = Role.USER_SPECTATOR;

    private static final User EXPECTED_USER = new User(null, EXPECTED_NAME, EXPECTED_LASTNAME, EXPECTED_NICKNAME,
            EXPECTED_EMAIL, EXPECTED_PASS, EXPECTED_ROLE, Collections.emptyList());

    private static final Long FIRST_USER_ID = 1L;

    private static final Long SECOND_USER_ID = 2L;

    private static final List<User> usersInDb = List.of(
            new User(FIRST_USER_ID, "First Name", "First Lastname", "First Nickname",
                    "first@email.gg", "First pass", Role.USER_PRESENTER, Collections.emptyList()),
            new User(SECOND_USER_ID, "Second Name", "Second Lastname", "Second Nickname",
                    "second@email.gg", "Second pass", Role.USER_ELECTOR, Collections.emptyList())
    );

    @Autowired
    private UserRepository userRepository;

    private User expected;
    private User actual;


    @Test
    @DisplayName("Create User: Should create an expected user and return user with generated ID")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void testCreateUser_ShouldCreateExpectedUser_AndReturnUserWithId() {
        final long expectedId = 3;
        expected = EXPECTED_USER;
        expected.setId(expectedId);

        actual = userRepository.save(EXPECTED_USER);

        // TODO figure out why bag related to sql scripts and sequence exist

        assertNotNull(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find User by ID: Should find user by ID and return expected user")
    void testFindById_ShouldFindUserById_AndReturnExpectedUser() {
        final long expectedId = 1;

        expected = usersInDb.get(0);

        actual = userRepository.findById(expectedId).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch user with id: " + expectedId));

        assertEquals(expectedId, actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Users: Should return all users and match expected users list")
    void testFindAllUsers_ShouldReturnAllUsers_AndReturnExpectedUsers() {
        final List<User> expected = new ArrayList<>(usersInDb);

        final List<User> actual = userRepository.findAll();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete User by ID: Should delete user with correct ID and return remaining users")
    void testDeleteUserById_ShouldDeleteUserWithCorrectId_AndFindAllShouldReturnOneUser() {
        final List<User> expected = new ArrayList<>(usersInDb);

        userRepository.deleteById(FIRST_USER_ID);

        expected.remove(0); // remove the first user from a list

        final List<User> actual = userRepository.findAll();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Insert Batch of Users: Should insert a batch of users and return expected list")
    void testInsertBatchOfUsers_ShouldInsertBatchOfUsers_AndShouldReturnExpectedList() {
        final List<User> userBatch = new ArrayList<>(usersInDb);
        userBatch.add(EXPECTED_USER);

        userBatch.forEach(user -> user.setId(null));

        final List<User> actualUsers = userRepository.saveAll(userBatch);

        final long[] id = {3};
        userBatch.forEach(user -> user.setId(id[0]++));

        assertEquals(userBatch, actualUsers);
    }

}