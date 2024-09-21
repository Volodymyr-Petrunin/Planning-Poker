package planing.poker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "Events")
@Getter
@Setter
public class Event {
    @Id
    private Long id;

    @OneToOne
    @JoinTable(name = "event_room")
    private Room room;

    @OneToMany
    private List<EventMessage> eventMessages;
}
