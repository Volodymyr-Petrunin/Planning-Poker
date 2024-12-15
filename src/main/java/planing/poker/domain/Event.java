package planing.poker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity(name = "Events")
@Getter
@Setter
@NamedEntityGraph(name = "event-with-room-entity-graph", attributeNodes = {
        @NamedAttributeNode("room")
})
public class Event {
    @Id
    @Column(unique = true, nullable = false, name = "event_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
    @SequenceGenerator(name = "event_seq", sequenceName = "event_seq", allocationSize = 10)
    private Long id;

    @OneToOne
    @JoinTable(
            name = "event_room",
            joinColumns = @JoinColumn(name = "event_event_id"),
            inverseJoinColumns = @JoinColumn(name = "room_room_id")
    )
    private Room room;

    @OneToMany
    @JoinTable(name = "events_messages_connection",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "event_message_id")
    )
    private List<EventMessage> eventMessages;

    public Event() {}

    public Event(final Long id, final Room room, final List<EventMessage> eventMessages) {
        this.id = id;
        this.room = room;
        this.eventMessages = eventMessages;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return Objects.equals(id, event.id) && Objects.equals(room, event.room)
                && Objects.equals(eventMessages, event.eventMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventMessages);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", room=" + room +
                ", eventMessages=" + eventMessages +
                '}';
    }
}
