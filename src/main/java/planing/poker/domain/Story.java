package planing.poker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "stories")
@Getter
@Setter
public class Story {
    @Id
    @Column(unique = true, nullable = false, name = "story_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "story_seq")
    @SequenceGenerator(name = "story_seq", sequenceName = "story_seq", allocationSize = 10)
    private Long id;

    @Column(nullable = false, name = "story_title")
    private String title;

    @Column(nullable = false, name = "story_link")
    private String storyLink;

    @OneToMany(mappedBy = "story")
    private List<Vote> votes;
}
