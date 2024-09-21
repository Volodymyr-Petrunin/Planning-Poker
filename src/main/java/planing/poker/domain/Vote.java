package planing.poker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
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
}
