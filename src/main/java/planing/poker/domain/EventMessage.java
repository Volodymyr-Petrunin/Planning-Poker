package planing.poker.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import planing.poker.common.exception.MessageParsingException;

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

    @Column(name = "event_message_key", nullable = false)
    private String messageKey;

    @Column(name = "event_message_args", columnDefinition = "TEXT")
    private String messageArgs;

    @Column(name = "event_message_timestamp", nullable = false)
    private LocalDateTime timestamp;

    public EventMessage() {

    }

    public EventMessage(final Long id, final User user, final String messageKey,
                        final String messageArgs, final LocalDateTime timestamp) {
        this.id = id;
        this.user = user;
        this.messageKey = messageKey;
        this.messageArgs= messageArgs;
        this.timestamp = timestamp;
    }

    public Object[] parseArgs() {
        if (messageArgs == null || messageArgs.isBlank()) {
            return new Object[0];
        }

        try {
            return new ObjectMapper().readValue(messageArgs, Object[].class);
        } catch (final JsonProcessingException e) {
            throw new MessageParsingException("Error parsing message arguments", e);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof EventMessage that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(messageKey, that.messageKey)
                && Objects.equals(messageArgs, that.messageArgs) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, messageKey, messageArgs, timestamp);
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "id=" + id +
                ", messageKey='" + messageKey + '\'' +
                ", messageArgs='" + messageArgs + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
