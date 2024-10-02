ALTER TABLE event_messages
    DROP CONSTRAINT IF EXISTS fk_event_messages_on_event_message_user;

ALTER TABLE event_messages
    ADD CONSTRAINT fk_event_messages_on_event_message_user FOREIGN KEY (event_message_user)
        REFERENCES users (user_id) ON DELETE CASCADE;

ALTER TABLE votes
    DROP CONSTRAINT IF EXISTS fk_votes_on_vote_voter;

ALTER TABLE votes
    ADD CONSTRAINT fk_votes_on_vote_voter FOREIGN KEY (vote_voter)
        REFERENCES users (user_id) ON DELETE CASCADE;