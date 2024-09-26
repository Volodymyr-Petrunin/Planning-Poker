package planing.poker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.domain.Story;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("Story Repository Tests")
@Sql(value = "classpath:script/story_repository.sql")
@Transactional
class StoryRepositoryTest {

    private static final Long EXPECTED_ID = 1L;

    private static final String EXPECTED_TITLE = "Sample Story Title";

    private static final String EXPECTED_LINK = "https://example.com/story";

    private static final Story EXPECTED_STORY = new Story(EXPECTED_ID, EXPECTED_TITLE, EXPECTED_LINK, Collections.emptyList());

    @Autowired
    private StoryRepository storyRepository;

    private Story expected;
    private Story actual;

    @Test
    @DisplayName("Create Story: Should create a story and return it with a generated ID")
    void testCreateStory_ShouldCreateExpectedStory_AndReturnStoryWithId() {
        expected = new Story(null, EXPECTED_TITLE, EXPECTED_LINK, Collections.emptyList());

        actual = storyRepository.save(expected);

        assertNotNull(actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getStoryLink(), actual.getStoryLink());
    }

    @Test
    @DisplayName("Find Story by ID: Should find a story by its ID and return the expected story")
    void testFindById_ShouldFindStoryById_AndReturnExpectedStory() {
        final Long storyId = EXPECTED_ID;
        expected = new Story(storyId, EXPECTED_TITLE, EXPECTED_LINK, Collections.emptyList());

        actual = storyRepository.findById(storyId).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch story with id: " + storyId));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Stories: Should return all stories and match the expected list")
    void testFindAllStories_ShouldReturnAllStories_AndReturnExpectedList() {
        assertEquals(List.of(EXPECTED_STORY), storyRepository.findAll());
    }

    @Test
    @DisplayName("Delete Story by ID: Should delete a story by ID and return the remaining stories")
    void testDeleteStoryById_ShouldDeleteStoryWithCorrectId_AndFindAllShouldReturnRemainingStories() {
        storyRepository.deleteById(EXPECTED_ID);

        final List<Story> remainingStories = storyRepository.findAll();

        assertTrue(remainingStories.isEmpty());
    }

    @Test
    @DisplayName("Insert Batch of Stories: Should insert a batch of stories and return the expected list")
    void testInsertBatchOfStories_ShouldInsertBatchOfStories_AndReturnExpectedList() {
        final List<Story> storyBatch = List.of(
                new Story(null, "First Story", "https://example.com/first-story", Collections.emptyList()),
                new Story(null, "Second Story", "https://example.com/second-story", Collections.emptyList())
        );

        final List<Story> actualStories = storyRepository.saveAll(storyBatch);

        assertEquals(storyBatch.size(), actualStories.size());
        assertEquals(storyBatch, actualStories);
    }
}