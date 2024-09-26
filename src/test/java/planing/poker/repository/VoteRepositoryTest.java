package planing.poker.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import planing.poker.common.Role;
import planing.poker.domain.Story;
import planing.poker.domain.User;
import planing.poker.domain.Vote;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql(value = "classpath:script/vote_repository.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
@DisplayName("Vote Repository Tests")
class VoteRepositoryTest {
    private static final Long EXPECTED_ID = 1L;

    private static final Integer EXPECTED_POINTS = 5;

    private static final User EXPECTED_ELECTOR = new User(EXPECTED_ID, "Voter Name", "Voter Lastname",
            "Voter Nickname", "voter@email.com", "voter_pass", Role.USER_ELECTOR, null);

    private static final Story EXPECTED_STORY = new Story(EXPECTED_ID, "Story Title", "Story Description", Collections.emptyList());

    private static final Vote EXPECTED_VOTE = new Vote(EXPECTED_ID, EXPECTED_ELECTOR, EXPECTED_POINTS, EXPECTED_STORY);

    @Autowired
    private VoteRepository voteRepository;

    private Vote expected;
    private Vote actual;

    @BeforeAll
    static void setUp() {
        EXPECTED_STORY.setVotes(List.of(EXPECTED_VOTE));
    }

    @Test
    @DisplayName("Create Vote: Should create a vote and return the vote with a generated ID")
    void testCreateVote_ShouldCreateExpectedVote_AndReturnVoteWithId() {
        expected = new Vote(null, EXPECTED_ELECTOR, EXPECTED_POINTS, EXPECTED_STORY);

        actual = voteRepository.save(expected);

        assertNotNull(actual.getId());
        assertEquals(expected.getVoter(), actual.getVoter());
        assertEquals(expected.getPoints(), actual.getPoints());
        assertEquals(expected.getStory(), actual.getStory());
    }

    @Test
    @DisplayName("Find Vote by ID: Should find a vote by its ID and return the expected vote")
    void testFindById_ShouldFindVoteById_AndReturnExpectedVote() {
        final Long voteId = EXPECTED_ID;
        expected = new Vote(voteId, EXPECTED_ELECTOR, EXPECTED_POINTS, EXPECTED_STORY);

        actual = voteRepository.findById(voteId).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch vote with id: " + voteId));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Votes: Should return all votes and match expected list")
    void testFindAllVotes_ShouldReturnAllVotes_AndReturnExpectedList() {
        final List<Vote> actual = voteRepository.findAll();

       assertEquals(List.of(EXPECTED_VOTE), actual);
    }

    @Test
    @DisplayName("Delete Vote by ID: Should delete a vote and return remaining votes")
    void testDeleteVoteById_ShouldDeleteVoteWithCorrectId_AndFindAllShouldReturnRemainingVotes() {
        voteRepository.deleteById(EXPECTED_ID);

        final List<Vote> remainingVotes = voteRepository.findAll();

        assertTrue(remainingVotes.isEmpty());
    }

    @Test
    @DisplayName("Insert Batch of Votes: Should insert a batch of votes and return the expected list")
    void testInsertBatchOfVotes_ShouldInsertBatchOfVotes_AndReturnExpectedList() {
        final User newUser = new User(null, "Other Voter", "Lastname", "Nickname",
                "other@email.com", "pass", Role.USER_ELECTOR, Collections.emptyList());

        final Story newStory = new Story(null, "Another Story", "Another Description", Collections.emptyList());

        final List<Vote> voteBatch = Arrays.asList(
                new Vote(null, EXPECTED_ELECTOR, 3, EXPECTED_STORY),
                new Vote(null, newUser,8, newStory)
        );

        final List<Vote> actualVotes = voteRepository.saveAll(voteBatch);

        newUser.setId(2L);
        newStory.setId(2L);

        voteBatch.forEach(vote -> vote.setVoter(newUser));
        voteBatch.forEach(vote -> vote.setStory(newStory));

        assertEquals(voteBatch.size(), actualVotes.size());
        assertEquals(voteBatch, actualVotes);
    }

}