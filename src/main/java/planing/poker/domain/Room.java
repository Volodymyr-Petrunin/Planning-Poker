package planing.poker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Room {
    @Id
    @Column(unique = true, nullable = false, name = "room_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    @SequenceGenerator(name = "room_seq", sequenceName = "room_seq", allocationSize = 10)
    private Long id;

    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "room_name", length = 100)
    private String roomName;

    @OneToOne
    @JoinColumn(name = "room_creator")
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "room_user",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> invitedUsers;

    @Column(name = "room_start_date")
    private LocalDate startDate;

    @Column(name = "room_start_time")
    private LocalTime startTime;

    @OneToOne
    @JoinColumn(name = "current_story_id")
    private Story currentStory;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "room_story",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "story_id")
    )
    private List<Story> stories;

    @Column(name = "room_vote_duration")
    private Duration voteDuration;

    @Column(name = "room_is_active")
    private Boolean isActive;

    @Column(name = "room_is_voting_open")
    private Boolean isVotingOpen;

    @Column(name = "room_voting_end_time")
    private LocalDateTime votingEndTime;

    @Column(name = "room_is_anonymous_voting")
    private Boolean isAnonymousVoting;

    @OneToOne(mappedBy = "room")
    private Event event;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return Objects.equals(id, room.id) && Objects.equals(roomCode, room.roomCode)
                && Objects.equals(roomName, room.roomName) && Objects.equals(creator.getId(), room.creator.getId())
                && Objects.equals(startDate, room.startDate) && Objects.equals(startTime, room.startTime)
                && Objects.equals(currentStory.getId(), room.currentStory.getId())
                && Objects.equals(voteDuration, room.voteDuration)
                && Objects.equals(isActive, room.isActive) && Objects.equals(isVotingOpen, room.isVotingOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomCode, roomName, startDate, startTime, voteDuration,
                isActive, isVotingOpen, votingEndTime, isAnonymousVoting);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomCode='" + roomCode + '\'' +
                ", roomName='" + roomName + '\'' +
                ", startDate=" + startDate +
                ", startTime=" + startTime +
                ", voteDuration=" + voteDuration +
                ", isActive=" + isActive +
                ", isVotingOpen=" + isVotingOpen +
                ", votingEndTime=" + votingEndTime +
                ", isAnonymousVoting=" + isAnonymousVoting +
                '}';
    }
}
