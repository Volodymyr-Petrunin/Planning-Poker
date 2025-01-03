package planing.poker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

@Entity(name = "votes")
@Getter
@Setter
@Accessors(chain = true)
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

    public Vote(final Long id, final User voter, final Integer points, final Story story) {
        this.id = id;
        this.voter = voter;
        this.points = points;
        this.story = story;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote vote)) return false;
        return Objects.equals(id, vote.id) && Objects.equals(voter.getId(), vote.voter.getId())
                && Objects.equals(points, vote.points) && Objects.equals(story.getId(), vote.story.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, voter, points, story);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", voter=" + voter.getNickname() +
                ", points=" + points +
                ", story=" + story.getTitle() +
                '}';
    }
}
