package planing.poker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "event_messages")
@Getter
@Setter
public class EventMessage {
    @Id
    @Column(unique = true, nullable = false, name = "event_message_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_message_seq")
    @SequenceGenerator(name = "event_message_seq", sequenceName = "event_message_seq", allocationSize = 100)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_message_user")
    private User user;

    @Column(name = "event_message_message")
    private String message;

    @Column(name = "event_message_timestamp")
    private LocalDateTime timestamp;

    public EventMessage() {

    }

    public EventMessage(Long id, User user, String message, LocalDateTime timestamp) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventMessage that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(message, that.message)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, message, timestamp);
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "id=" + id +
                ", user=" + user +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
