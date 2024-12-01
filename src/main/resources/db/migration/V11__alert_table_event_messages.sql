ALTER TABLE event_messages
    ADD event_message_args TEXT;

ALTER TABLE event_messages
    ADD event_message_key VARCHAR(255);

ALTER TABLE event_messages
    ALTER COLUMN event_message_key SET NOT NULL;

ALTER TABLE event_messages
    DROP COLUMN event_message_message;

ALTER TABLE event_messages
    ALTER COLUMN event_message_timestamp SET NOT NULL;

ALTER TABLE votes
    ADD CONSTRAINT uc_votes_vote_voter_story UNIQUE (vote_voter, story_id);
