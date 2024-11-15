package planing.poker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.Role;
import planing.poker.domain.User;
import planing.poker.factory.UserFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static planing.poker.factory.utils.ExpectedEntityUtils.getUserCreator;
import static planing.poker.factory.utils.ExpectedEntityUtils.getUserElector;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("User Repository Tests")
@Sql(scripts = {"classpath:script/init_expected_data.sql"})
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User expected;
    private User actual;


    @Test
    @DisplayName("Create User: Should create an expected user and return user with generated ID")
    void testCreateUser_ShouldCreateExpectedUser_AndReturnUserWithId() {
        final long expectedId = 3;
        expected = UserFactory.createCustomUser(null, "New", "User",
                "NEW_USER", "newUser@email", "newPass", Role.USER_ELECTOR);

        actual = userRepository.save(expected);
        expected.setId(expectedId);

        assertNotNull(actual.getId());
        assertEquals(expectedId, actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find User by ID: Should find user by ID and return expected user")
    void testFindById_ShouldFindUserById_AndReturnExpectedUser() {
        final long expectedId = 1;

        expected = getUserElector();

        actual = userRepository.findById(expectedId).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch user with id: " + expectedId));

        assertEquals(expectedId, actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Users: Should return all users and match expected users list")
    void testFindAllUsers_ShouldReturnAllUsers_AndReturnExpectedUsers() {
        final List<User> expected = List.of(getUserElector(), getUserCreator());

        final List<User> actual = userRepository.findAll();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete User by ID: Should delete user with correct ID and return remaining users")
    void testDeleteUserById_ShouldDeleteUserWithCorrectId_AndFindAllShouldReturnOneUser() {
        userRepository.deleteById(getUserElector().getId());

        final List<User> actual = userRepository.findAll();

        assertEquals(List.of(getUserCreator()), actual);
    }

    @Test
    @DisplayName("Insert Batch of Users: Should insert a batch of users and return expected list")
    void testInsertBatchOfUsers_ShouldInsertBatchOfUsers_AndShouldReturnExpectedList() {
        final List<User> userBatch = new ArrayList<>(List.of(getUserElector(), getUserCreator()));
        userBatch.add(new User().setFirstName("First").setLastName("Last").setRoles(Collections.emptyList()));

        userBatch.forEach(user -> user.setId(null));

        final List<User> actualUsers = userRepository.saveAll(userBatch);

        final long[] id = {3};
        userBatch.forEach(user -> user.setId(id[0]++));

        assertEquals(userBatch, actualUsers);
    }

}