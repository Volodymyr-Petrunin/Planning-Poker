package planing.poker.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import planing.poker.domain.SecurityRole;
import planing.poker.domain.User;
import planing.poker.domain.dto.request.RequestUserDto;
import planing.poker.domain.dto.response.ResponseUserDto;
import planing.poker.mapper.UserMapper;
import planing.poker.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Value("${max-page-size.user}")
    private int maxPageSizeForUser;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository, final UserMapper userMapper,
                       final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseUserDto createUser(final RequestUserDto userDto) {
        final User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSecurityRole(SecurityRole.ROLE_USER);

        return userMapper.toDto(userRepository.save(user));
    }

    public List<ResponseUserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public List<ResponseUserDto> getUsersByExample(final RequestUserDto userDto) {
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
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object")));
    }

    public ResponseUserDto updateUser(final RequestUserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    public ResponseUserDto getUserByEmail(final String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("message.not.find.object");
        }
        return userMapper.toDto(user);
    }
}
