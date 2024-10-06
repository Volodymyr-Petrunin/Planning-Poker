package planing.poker.factory.dto;

import planing.poker.domain.dto.StoryDto;

import java.util.Collections;

public class StoryDtoFactory {
    private static final Long EXPECTED_ID = 1L;
    private static final String EXPECTED_TITLE = "Story Title";
    private static final String EXPECTED_LINK = "Story Link";

    public static StoryDto createStory() {
        return new StoryDto(EXPECTED_ID, EXPECTED_TITLE, EXPECTED_LINK, Collections.emptyList());
    }
}
