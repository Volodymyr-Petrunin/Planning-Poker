package planing.poker.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import planing.poker.common.exception.MessageParsingException;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "event_messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    public boolean equals(final Object object) {
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
