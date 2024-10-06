package planing.poker.factory.dto;

import planing.poker.common.Role;
import planing.poker.domain.SecurityRole;
import planing.poker.domain.dto.UserDto;

public class UserDtoFactory {
    private static final Long EXPECTED_ID = 1L;
    private static final Long EXPECTED_CREATOR_ID = 2L;

    private static final String EXPECTED_NAME = "Expected Name";
    private static final String EXPECTED_LASTNAME = "Expected Lastname";
    private static final String EXPECTED_NICKNAME = "Expected Nickname";
    private static final String EXPECTED_EMAIL = "expected@email.gg";
    private static final String EXPECTED_PASS = "Expected pass";
    private static final Role EXPECTED_ROLE = Role.USER_ELECTOR;
    private static final SecurityRole EXPECTED_SECURITY_ROLE = SecurityRole.ROLE_USER;

    private static final String CREATOR_NAME = "User";
    private static final String CREATOR_LASTNAME = "Creator";
    private static final String CREATOR_NICKNAME = "UserCreator";
    private static final String CREATOR_EMAIL = "UserCreator@email.gg";
    private static final String CREATOR_PASS = "UserCreatorPass";
    private static final Role CREATOR_ROLE = Role.USER_PRESENTER;
    private static final SecurityRole CREATOR_SECURITY_ROLE = SecurityRole.ROLE_ADMIN;

    public static UserDto createExpectedElectorDto() {
        return new UserDto()
                .setId(EXPECTED_ID)
                .setFirstName(EXPECTED_NAME)
                .setLastName(EXPECTED_LASTNAME)
                .setNickname(EXPECTED_NICKNAME)
                .setEmail(EXPECTED_EMAIL)
                .setPassword(EXPECTED_PASS)
                .setRole(EXPECTED_ROLE)
                .setSecurityRole(EXPECTED_SECURITY_ROLE);
    }

    public static UserDto createExpectedElectorDto(final Long id) {
        return new UserDto()
                .setId(id)
                .setFirstName(EXPECTED_NAME)
                .setLastName(EXPECTED_LASTNAME)
                .setNickname(EXPECTED_NICKNAME)
                .setEmail(EXPECTED_EMAIL)
                .setPassword(EXPECTED_PASS)
                .setRole(EXPECTED_ROLE)
                .setSecurityRole(EXPECTED_SECURITY_ROLE);
    }

    public static UserDto createExpectedCreatorDto() {
        return new UserDto()
                .setId(EXPECTED_CREATOR_ID)
                .setFirstName(CREATOR_NAME)
                .setLastName(CREATOR_LASTNAME)
                .setNickname(CREATOR_NICKNAME)
                .setEmail(CREATOR_EMAIL)
                .setPassword(CREATOR_PASS)
                .setRole(CREATOR_ROLE)
                .setSecurityRole(CREATOR_SECURITY_ROLE);
    }

    public static UserDto createCustomUserDto(final Long id, final String name, final String lastName,
                                        final String nickname, final String email, final String password,
                                        final Role role) {
        return new UserDto()
                .setId(id)
                .setFirstName(name)
                .setLastName(lastName)
                .setNickname(nickname)
                .setEmail(email)
                .setPassword(password)
                .setRole(role);
    }
}
