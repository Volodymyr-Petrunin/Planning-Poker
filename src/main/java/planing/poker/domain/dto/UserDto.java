package planing.poker.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import planing.poker.common.Role;

import java.util.List;

/**
 * DTO for {@link planing.poker.domain.User}
 */

@Getter
@Setter
public class UserDto {
    @Positive
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @Size(min = 2, max = 100)
    private String nickname;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Role role;

    @NotNull
    private List<RoomDto> rooms;
}