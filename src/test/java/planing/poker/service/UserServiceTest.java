package planing.poker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import planing.poker.domain.User;
import planing.poker.domain.dto.UserDto;
import planing.poker.factory.utils.ExpectedEntityDtoUtils;
import planing.poker.factory.utils.ExpectedEntityUtils;
import planing.poker.mapper.UserMapper;
import planing.poker.repository.UserRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@DisplayName("User Service Tests")
class UserServiceTest {
    private static final UserDto EXPECTED_DTO = ExpectedEntityDtoUtils.getUserElector();
    private static final User EXPECTED_ENTITY = ExpectedEntityUtils.getUserElector();

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("Create User: Should create user and return correct DTO")
    void testCreateUser_ShouldCreateUser_AndReturnCorrectDto() {
        when(userMapper.toEntity(EXPECTED_DTO)).thenReturn(EXPECTED_ENTITY);
        when(userRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(userMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final UserDto createdUser = userService.createUser(EXPECTED_DTO);

        assertNotNull(createdUser);

        verify(userRepository, times(1)).save(EXPECTED_ENTITY);
        verify(userMapper, times(1)).toEntity(EXPECTED_DTO);
        verify(userMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get All Users: Should return all users as a list of DTOs")
    void testGetAllUsers_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(EXPECTED_ENTITY));
        when(userMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get User By ID: Should return the correct user DTO for given ID")
    void testGetUserById_ShouldReturnUser() {
        when(userRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.of(EXPECTED_ENTITY));
        when(userMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final UserDto user = userService.getUserById(EXPECTED_DTO.getId());

        assertNotNull(user);

        verify(userRepository, times(1)).findById(EXPECTED_DTO.getId());
        verify(userMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Get User By ID: Should throw exception if user is not found")
    void testGetUserById_ShouldThrowExceptionIfNotFound() {
        when(userRepository.findById(EXPECTED_DTO.getId())).thenReturn(Optional.empty());

        final Exception exception = assertThrows(
                IllegalArgumentException.class, () -> userService.getUserById(EXPECTED_DTO.getId()));

        assertEquals("message.not.find.object", exception.getMessage());

        verify(userRepository, times(1)).findById(EXPECTED_DTO.getId());
    }

    @Test
    @DisplayName("Update User: Should update user and return the updated DTO")
    void testUpdateUser_ShouldUpdateUser_AndReturnUpdatedDto() {
        when(userMapper.toEntity(EXPECTED_DTO)).thenReturn(EXPECTED_ENTITY);
        when(userRepository.save(EXPECTED_ENTITY)).thenReturn(EXPECTED_ENTITY);
        when(userMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final UserDto updatedUser = userService.updateUser(EXPECTED_DTO);

        assertNotNull(updatedUser);

        verify(userRepository, times(1)).save(EXPECTED_ENTITY);
        verify(userMapper, times(1)).toEntity(EXPECTED_DTO);
        verify(userMapper, times(1)).toDto(EXPECTED_ENTITY);
    }

    @Test
    @DisplayName("Delete User: Should delete user by given ID")
    void testDeleteUser_ShouldDeleteUser() {
        doNothing().when(userRepository).deleteById(EXPECTED_DTO.getId());

        userService.deleteUser(EXPECTED_DTO.getId());

        verify(userRepository, times(1)).deleteById(EXPECTED_DTO.getId());
    }

    @Test
    @DisplayName("Get User By Email: Should return the correct user DTO for given email")
    void testGetUserByEmail_ShouldReturnUserByEmail() {
        when(userRepository.findByEmail(EXPECTED_DTO.getEmail())).thenReturn(EXPECTED_ENTITY);
        when(userMapper.toDto(EXPECTED_ENTITY)).thenReturn(EXPECTED_DTO);

        final UserDto user = userService.getUserByEmail(EXPECTED_DTO.getEmail());

        assertNotNull(user);

        verify(userRepository, times(1)).findByEmail(EXPECTED_DTO.getEmail());
        verify(userMapper, times(1)).toDto(EXPECTED_ENTITY);
    }
}
