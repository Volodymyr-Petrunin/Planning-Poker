package planing.poker.domain.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planing.poker.common.Role;

/**
 * DTO for {@link planing.poker.domain.RoomUserRole}
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRoomUserRoleDto {

    @NotNull
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long roomId;

    @NotNull
    private Role role;
}
