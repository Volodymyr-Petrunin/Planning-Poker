package planing.poker.domain.dto.request;

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
@AllArgsConstructor
@NoArgsConstructor
public class RequestRoomUserRoleDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long roomId;

    @NotNull
    private String newRole;
}
