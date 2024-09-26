package planing.poker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import planing.poker.common.Role;

import java.util.List;
import java.util.Objects;

@Entity(name = "users")
@Getter
@Setter
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

    @Column(name = "user_role", length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "invitedUsers")
    private List<Room> rooms;

    public User() {
    }

    public User(final Long id, final String firstName, final String lastName, final String nickname,
                final String email, final String password, final Role role, final List<Room> rooms) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName) && Objects.equals(nickname, user.nickname)
                && Objects.equals(email, user.email) && Objects.equals(password, user.password)
                && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nickname, email, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
