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
import planing.poker.factory.utils.ExpectedEntityUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static planing.poker.factory.utils.ExpectedEntityUtils.getVote;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("Vote Repository Tests")
@Sql(scripts = {"classpath:script/init_expected_data.sql"})
@Transactional
class VoteRepositoryTest {

    private static final User EXPECTED_ELECTOR = ExpectedEntityUtils.getUserElector();

    private static final Story EXPECTED_STORY = ExpectedEntityUtils.getStory();

    private static final Vote EXPECTED_VOTE = getVote();

    private static final Integer EXPECTED_POINTS = 3;

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
        expected = getVote();

        actual = voteRepository.findById(expected.getId()).orElseThrow(
                () -> new IllegalArgumentException("Can't fetch vote with id: " + expected.getId()));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find All Votes: Should return all votes and match expected list")
    void testFindAllVotes_ShouldReturnAllVotes_AndReturnExpectedList() {
       assertEquals(List.of(getVote()), voteRepository.findAll());
    }

    @Test
    @DisplayName("Delete Vote by ID: Should delete a vote and return remaining votes")
    void testDeleteVoteById_ShouldDeleteVoteWithCorrectId_AndFindAllShouldReturnRemainingVotes() {
        voteRepository.deleteById(getVote().getId());

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
                new Vote(null, EXPECTED_ELECTOR, EXPECTED_POINTS, EXPECTED_STORY),
                new Vote(null, newUser,EXPECTED_POINTS, newStory)
        );

        final List<Vote> actualVotes = voteRepository.saveAll(voteBatch);

        newUser.setId(3L); // set id 3 because we insert two users each time
        newStory.setId(2L);

        voteBatch.forEach(vote -> vote.setVoter(newUser));
        voteBatch.forEach(vote -> vote.setStory(newStory));

        assertEquals(voteBatch.size(), actualVotes.size());
        assertEquals(voteBatch, actualVotes);
    }

}