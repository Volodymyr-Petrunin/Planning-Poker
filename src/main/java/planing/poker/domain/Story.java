package planing.poker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    public Story() {
    }

    public Story(final Long id, final String title, final String storyLink, final List<Vote> votes) {
        this.id = id;
        this.title = title;
        this.storyLink = storyLink;
        this.votes = votes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Story story)) return false;
        return Objects.equals(id, story.id) && Objects.equals(title, story.title)
                && Objects.equals(storyLink, story.storyLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, storyLink);
    }

    @Override
    public String toString() {
        return "Story{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", storyLink='" + storyLink + '\'' +
                ", votes=" + votes +
                '}';
    }
}
