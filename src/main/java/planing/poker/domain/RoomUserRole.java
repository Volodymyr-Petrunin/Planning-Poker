package planing.poker.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import planing.poker.common.Role;

@Entity(name = "room_user_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RoomUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_user_role_seq")
    @SequenceGenerator(name = "room_user_role_seq", sequenceName = "room_user_role_seq", allocationSize = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "role", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
}
