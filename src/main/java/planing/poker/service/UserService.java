package planing.poker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planing.poker.domain.dto.UserDto;
import planing.poker.mapper.UserMapper;
import planing.poker.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(final UserRepository userRepository, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto createUser(final UserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public UserDto getUserById(final Long id) {
        return userMapper.toDto(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("message.not.find.object")));
    }

    public UserDto updateUser(final UserDto userDto) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDto)));
    }

    public void deleteUser(final Long id) {
        userRepository.deleteById(id);
    }

    public UserDto getUserByEmail(final String email) {
        return userMapper.toDto(userRepository.findByEmail(email));
    }
}
