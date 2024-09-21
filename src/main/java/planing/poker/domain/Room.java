package planing.poker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity(name = "rooms")
@Getter
@Setter
public class Room {
    @Id
    @Column(unique = true, nullable = false, name = "room_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    @SequenceGenerator(name = "room_seq", sequenceName = "room_seq", allocationSize = 10)
    private Long id;

    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "room_name")
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
    private Date startDate;

    @Column(name = "room_start_time")
    private LocalTime startTime;

    @OneToOne
    @JoinColumn(name = "current_story_id")
    private Story currentStory;

    @ManyToMany
    @JoinTable(
            name = "room_story",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "story_id")
    )
    private List<Story> stories;

    @Column(name = "room_vote_duration")
    private LocalTime voteDuration;

    @Column(name = "room_is_active")
    private Boolean isActive;

    @Column(name = "room_is_voting_open")
    private Boolean isVotingOpen;
}
