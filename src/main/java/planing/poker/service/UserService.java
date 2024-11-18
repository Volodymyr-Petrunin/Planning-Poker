package planing.poker.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import planing.poker.common.ExceptionMessages;
import planing.poker.common.Role;
import planing.poker.domain.SecurityRole;
import planing.poker.domain.User;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.domain.dto.response.ResponseRoomUserRoleDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.event.user.UserChangeRoleEvent;
import planing.poker.event.user.UserCreatedEvent;
import planing.poker.event.user.UserDeletedEvent;
import planing.poker.event.user.UserUpdatedEvent;
import planing.poker.mapper.UserMapper;
import planing.poker.repository.UserRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Value("${max.page.size.user}")
    private int maxPageSizeForUser;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final ExceptionMessages exceptionMessages;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserService(final UserRepository userRepository, final UserMapper userMapper,
                       final PasswordEncoder passwordEncoder, final ExceptionMessages exceptionMessages,
                       final ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.exceptionMessages = exceptionMessages;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ResponseUserDto createUser(final RequestUserDto userDto) {
        final User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSecurityRole(SecurityRole.ROLE_USER);

        final ResponseUserDto savedUser = userMapper.toDto(userRepository.save(user));
        applicationEventPublisher.publishEvent(new UserCreatedEvent(savedUser));

        return savedUser;
    }

    public List<ResponseUserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public List<ResponseUserDto> getUsersByExample(final RequestUserDto userDto) {
        if (isAllBlank(userDto.getFirstName(), userDto.getLastName(), userDto.getNickname())) {
            return Collections.emptyList();
        }

        final User user = userMapper.toEntity(userDto);

        final Pageable limit = PageRequest.of(0, maxPageSizeForUser);

        final Example<User> example = Example.of(user,
            ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        return userRepository.findAll(example, limit).stream().map(userMapper::toDto).toList();
    }

    public ResponseUserDto getUserById(final Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE())));
    }

    public ResponseUserDto updateUser(final long id, final RequestUserDto userDto) {
        if (userRepository.findById(id).isPresent()) {
            final User user = userMapper.toEntity(userDto);
            user.setId(id);

            final ResponseUserDto updatedUser = userMapper.toDto(userRepository.save(user));
            applicationEventPublisher.publishEvent(new UserUpdatedEvent(updatedUser));

            return updatedUser;
        } else {
            throw new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE());
        }
    }

    public ResponseUserDto updateUserRole(final long roomId,final long userId, final Role role) {
        final ResponseUserDto user = getUserById(userId);
        updateRole(user, roomId, role);

        final ResponseUserDto updatedUser = userMapper.toDto(userRepository.save(userMapper.responseToEntity(user)));

        applicationEventPublisher.publishEvent(new UserChangeRoleEvent(updatedUser));
        return updatedUser;
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
        applicationEventPublisher.publishEvent(new UserDeletedEvent(id));
    }

    public ResponseUserDto getUserByEmail(final String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException(exceptionMessages.NO_FIND_MESSAGE());
        }
        return userMapper.toDto(user);
    }

    private boolean isAllBlank(final String... strings) {
        return Arrays.stream(strings).allMatch(str -> str == null || str.trim().isEmpty());
    }

    private void updateRole(final ResponseUserDto user, final Long roomId, final Role role) {
        final List<ResponseRoomUserRoleDto> roles = user.getRoles();
        roles.stream()
                .filter(currentRole -> currentRole.getRoomId().equals(roomId))
                .findFirst()
                .ifPresent(currentRole -> currentRole.setRole(role));

        user.setRoles(roles);
    }
}
