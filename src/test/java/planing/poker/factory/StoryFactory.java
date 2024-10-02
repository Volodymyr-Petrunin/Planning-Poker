package planing.poker.factory;

import planing.poker.domain.Story;
import planing.poker.domain.Vote;

import java.util.Collections;
import java.util.List;

public class StoryFactory {
    private static final Long EXPECTED_ID = 1L;
    private static final String EXPECTED_TITLE = "Story Title";
    private static final String EXPECTED_LINK = "Story Link";

    public static Story createStory() {
        return new Story(EXPECTED_ID, EXPECTED_TITLE, EXPECTED_LINK, Collections.emptyList());
    }

    public static Story createStory(final Long id, final String title, final String link) {
        return new Story(id, title, link, Collections.emptyList());
    }

    public static Story createStory(final Long id, final String title, final String link, final List<Vote> votes) {
        return new Story(id, title, link, votes);
    }
}
