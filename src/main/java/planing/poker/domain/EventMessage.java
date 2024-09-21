package planing.poker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
