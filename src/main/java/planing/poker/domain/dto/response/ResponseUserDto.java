package planing.poker.domain.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import planing.poker.common.Role;
import planing.poker.domain.RoomUserRole;
import planing.poker.domain.SecurityRole;

import java.util.List;

/**
 * DTO for {@link planing.poker.domain.User}
 */

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
public class ResponseUserDto {
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
    private List<ResponseRoomUserRoleDto> roles;

    @NotNull
    private SecurityRole securityRole;

    @NotNull
    private List<Long> roomsId;

    private List<ResponseTeamDto> teams;

    private Role roomRole;
}