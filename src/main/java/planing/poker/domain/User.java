package planing.poker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import planing.poker.commons.Role;

import java.util.List;

@Entity(name = "users")
@Getter
@Setter
public class User {
    @Id
    @Column(unique = true, nullable = false, name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 50)
    private Long id;

    @Column(name = "user_firstname")
    private String firstName;

    @Column(name = "user_lastname")
    private String lastName;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "invitedUsers")
    private List<Room> rooms;
}
