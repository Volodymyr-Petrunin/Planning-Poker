ALTER TABLE rooms
    DROP COLUMN room_start_date;

ALTER TABLE rooms
    DROP COLUMN room_vote_duration;

ALTER TABLE rooms
    ADD room_start_date date;

ALTER TABLE rooms
    ADD room_vote_duration BIGINT;