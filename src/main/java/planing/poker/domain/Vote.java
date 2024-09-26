package planing.poker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "votes")
@Getter
@Setter
public class Vote {
    @Id
    @Column(unique = true, nullable = false, name = "vote_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_seq")
    @SequenceGenerator(name = "vote_seq", sequenceName = "vote_seq", allocationSize = 30)
    private Long id;

    @OneToOne
    @JoinColumn(name = "vote_voter")
    private User voter;

    @Column(name = "story_points")
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;

    public Vote() {
    }

    public Vote(Long id, User voter, Integer points, Story story) {
        this.id = id;
        this.voter = voter;
        this.points = points;
        this.story = story;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote vote)) return false;
        return Objects.equals(id, vote.id) && Objects.equals(voter, vote.voter)
                && Objects.equals(points, vote.points) && Objects.equals(story, vote.story);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, voter, points, story);
    }
}
