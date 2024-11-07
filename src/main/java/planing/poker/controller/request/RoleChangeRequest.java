package planing.poker.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleChangeRequest {
    private Long userId;
    private String newRole;
}
