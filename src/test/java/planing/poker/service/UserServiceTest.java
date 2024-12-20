package planing.poker.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import planing.poker.common.ExceptionMessages;
import planing.poker.common.factory.EventMessageFactory;
import planing.poker.domain.User;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.domain.dto.update.UpdateUserDto;
import planing.poker.mapper.UserMapper;
import planing.poker.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@DisplayName("User Service Tests")
class UserServiceTest {

    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .defaultNotNull(true)
            .build();

    private static ResponseUserDto expectedResponseDto;

    private static RequestUserDto expectedRequestDto;

    private static UpdateUserDto expectedUpdateUserDto;

    private static User expectedEntity;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ExceptionMessages exceptionMessages;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private EventMessageFactory eventMessageFactory;

    @BeforeAll
    static void setUp() {
        expectedResponseDto = fixtureMonkey.giveMeOne(ResponseUserDto.class);
        expectedRequestDto = fixtureMonkey.giveMeBuilder(RequestUserDto.class)
                .validOnly(false)
                .sample();
        expectedUpdateUserDto = fixtureMonkey.giveMeBuilder(UpdateUserDto.class)
                .validOnly(false)
                .sample();
        expectedEntity = fixtureMonkey.giveMeOne(User.class);
    }

    @Test
    @DisplayName("Create User: Should create user and return correct DTO")
    void testCreateUser_ShouldCreateUser_AndReturnCorrectDto() {
        when(userMapper.toEntity(expectedRequestDto)).thenReturn(expectedEntity);
        when(passwordEncoder.encode(anyString())).thenReturn(expectedResponseDto.getPassword());
        when(userRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(userMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseUserDto createdUser = userService.createUser(expectedRequestDto);

        assertNotNull(createdUser);

        verify(userRepository, times(1)).save(expectedEntity);
        verify(userMapper, times(1)).toDto(expectedEntity);
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    @DisplayName("Get All Users: Should return all users as a list of DTOs")
    void testGetAllUsers_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(expectedEntity));
        when(userMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final List<ResponseUserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());

        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get User By ID: Should return the correct user DTO for given ID")
    void testGetUserById_ShouldReturnUser() {
        when(userRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.of(expectedEntity));
        when(userMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseUserDto user = userService.getUserById(expectedResponseDto.getId());

        assertNotNull(user);

        verify(userRepository, times(1)).findById(expectedResponseDto.getId());
        verify(userMapper, times(1)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Get User By ID: Should throw exception if user is not found")
    void testGetUserById_ShouldThrowExceptionIfNotFound() {
        when(userRepository.findById(expectedResponseDto.getId())).thenReturn(Optional.empty());
        when(exceptionMessages.NO_FIND_MESSAGE()).thenReturn("Error Message!");

        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(expectedResponseDto.getId()));

        verify(userRepository, times(1)).findById(expectedResponseDto.getId());
    }

    @Test
    @DisplayName("Update User: Should update user and return the updated DTO")
    void testUpdateUser_ShouldUpdateUser_AndReturnUpdatedDto() {
        when(userRepository.findById(expectedUpdateUserDto.getId())).thenReturn(Optional.of(expectedEntity));
        when(userMapper.responseToEntity(expectedResponseDto)).thenReturn(expectedEntity);
        when(userRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(userMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseUserDto updatedUser = userService.updateUser(expectedUpdateUserDto);

        assertNotNull(updatedUser);

        verify(userRepository, times(1)).save(expectedEntity);
        verify(userMapper, times(2)).toDto(expectedEntity);
    }

    @Test
    @DisplayName("Delete User: Should delete user by given ID")
    void testDeleteUser_ShouldDeleteUser() {
        doNothing().when(userRepository).deleteById(expectedResponseDto.getId());

        userService.deleteUser(expectedResponseDto.getId());

        verify(userRepository, times(1)).deleteById(expectedResponseDto.getId());
    }

    @Test
    @DisplayName("Get User By Email: Should return the correct user DTO for given email")
    void testGetUserByEmail_ShouldReturnUserByEmail() {
        when(userRepository.findByEmail(expectedResponseDto.getEmail())).thenReturn(expectedEntity);
        when(userMapper.toDto(expectedEntity)).thenReturn(expectedResponseDto);

        final ResponseUserDto user = userService.getUserByEmail(expectedResponseDto.getEmail());

        assertNotNull(user);

        verify(userRepository, times(1)).findByEmail(expectedResponseDto.getEmail());
        verify(userMapper, times(1)).toDto(expectedEntity);
    }
}
