package planing.poker.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    @Id
    @Column(unique = true, nullable = false, name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 50)
    private Long id;

    @Column(name = "user_firstname", length = 50)
    private String firstName;

    @Column(name = "user_lastname", length = 50)
    private String lastName;

    @Column(name = "user_nickname", length = 100)
    private String nickname;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomUserRole> roles;

    @Column(name = "security_role", length = 20)
    @Enumerated(EnumType.STRING)
    private SecurityRole securityRole;

    @ManyToMany(mappedBy = "invitedUsers")
    private List<Room> rooms;

    @ManyToMany(mappedBy = "members")
    private List<Team> teams;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName) && Objects.equals(nickname, user.nickname)
                && Objects.equals(email, user.email) && Objects.equals(password, user.password)
                && securityRole == user.securityRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nickname, email, password, securityRole);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", securityRole=" + securityRole +
                '}';
    }
}
