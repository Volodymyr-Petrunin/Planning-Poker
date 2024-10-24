ALTER TABLE rooms DROP CONSTRAINT uc_rooms_room_creator;
ALTER TABLE votes DROP CONSTRAINT uc_votes_vote_voter;

ALTER TABLE rooms
    DROP COLUMN room_start_date;

ALTER TABLE rooms
    DROP COLUMN room_vote_duration;

ALTER TABLE rooms
    ADD room_start_date date;

ALTER TABLE rooms
    ADD room_vote_duration BIGINT;