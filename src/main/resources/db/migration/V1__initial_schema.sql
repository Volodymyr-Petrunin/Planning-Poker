CREATE SEQUENCE IF NOT EXISTS event_message_seq START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE IF NOT EXISTS room_seq START WITH 1 INCREMENT BY 10;

CREATE SEQUENCE IF NOT EXISTS story_seq START WITH 1 INCREMENT BY 10;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS vote_seq START WITH 1 INCREMENT BY 30;

CREATE TABLE event_messages
(
    event_message_id        BIGINT NOT NULL,
    event_message_user      BIGINT,
    event_message_message   VARCHAR(255),
    event_message_timestamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_event_messages PRIMARY KEY (event_message_id)
);

CREATE TABLE event_room
(
    events_id    BIGINT NOT NULL,
    room_room_id BIGINT,
    CONSTRAINT pk_event_room PRIMARY KEY (events_id)
);

CREATE TABLE events
(
    id BIGINT NOT NULL,
    CONSTRAINT pk_events PRIMARY KEY (id)
);

CREATE TABLE events_event_messages
(
    events_id                       BIGINT NOT NULL,
    event_messages_event_message_id BIGINT NOT NULL
);

CREATE TABLE room_story
(
    room_id  BIGINT NOT NULL,
    story_id BIGINT NOT NULL
);

CREATE TABLE room_user
(
    room_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE rooms
(
    room_id             BIGINT NOT NULL,
    room_code           VARCHAR(255),
    room_name           VARCHAR(100),
    room_creator        BIGINT,
    room_start_date     TIMESTAMP WITHOUT TIME ZONE,
    room_start_time     time WITHOUT TIME ZONE,
    current_story_id    BIGINT,
    room_vote_duration  time WITHOUT TIME ZONE,
    room_is_active      BOOLEAN,
    room_is_voting_open BOOLEAN,
    CONSTRAINT pk_rooms PRIMARY KEY (room_id)
);

CREATE TABLE stories
(
    story_id    BIGINT       NOT NULL,
    story_title VARCHAR(255) NOT NULL,
    story_link  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_stories PRIMARY KEY (story_id)
);

CREATE TABLE users
(
    user_id        BIGINT NOT NULL,
    user_firstname VARCHAR(50),
    user_lastname  VARCHAR(50),
    user_nickname  VARCHAR(100),
    user_email     VARCHAR(255),
    user_password  VARCHAR(255),
    user_role      VARCHAR(20),
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

CREATE TABLE votes
(
    vote_id      BIGINT NOT NULL,
    vote_voter   BIGINT,
    story_points INTEGER,
    story_id     BIGINT,
    CONSTRAINT pk_votes PRIMARY KEY (vote_id)
);

ALTER TABLE events_event_messages
    ADD CONSTRAINT uc_events_event_messages_eventmessages_event_message UNIQUE (event_messages_event_message_id);

ALTER TABLE rooms
    ADD CONSTRAINT uc_rooms_current_story UNIQUE (current_story_id);

ALTER TABLE rooms
    ADD CONSTRAINT uc_rooms_room_creator UNIQUE (room_creator);

ALTER TABLE votes
    ADD CONSTRAINT uc_votes_vote_voter UNIQUE (vote_voter);

ALTER TABLE event_messages
    ADD CONSTRAINT FK_EVENT_MESSAGES_ON_EVENT_MESSAGE_USER FOREIGN KEY (event_message_user) REFERENCES users (user_id);

ALTER TABLE rooms
    ADD CONSTRAINT FK_ROOMS_ON_CURRENT_STORY FOREIGN KEY (current_story_id) REFERENCES stories (story_id);

ALTER TABLE rooms
    ADD CONSTRAINT FK_ROOMS_ON_ROOM_CREATOR FOREIGN KEY (room_creator) REFERENCES users (user_id);

ALTER TABLE votes
    ADD CONSTRAINT FK_VOTES_ON_STORY FOREIGN KEY (story_id) REFERENCES stories (story_id);

ALTER TABLE votes
    ADD CONSTRAINT FK_VOTES_ON_VOTE_VOTER FOREIGN KEY (vote_voter) REFERENCES users (user_id);

ALTER TABLE events_event_messages
    ADD CONSTRAINT fk_eveevemes_on_event FOREIGN KEY (events_id) REFERENCES events (id);

ALTER TABLE events_event_messages
    ADD CONSTRAINT fk_eveevemes_on_event_message FOREIGN KEY (event_messages_event_message_id) REFERENCES event_messages (event_message_id);

ALTER TABLE event_room
    ADD CONSTRAINT fk_everoo_on_event FOREIGN KEY (events_id) REFERENCES events (id);

ALTER TABLE event_room
    ADD CONSTRAINT fk_everoo_on_room FOREIGN KEY (room_room_id) REFERENCES rooms (room_id);

ALTER TABLE room_user
    ADD CONSTRAINT fk_room_user_on_room FOREIGN KEY (room_id) REFERENCES rooms (room_id);

ALTER TABLE room_user
    ADD CONSTRAINT fk_room_user_on_user FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE room_story
    ADD CONSTRAINT fk_roosto_on_room FOREIGN KEY (room_id) REFERENCES rooms (room_id);

ALTER TABLE room_story
    ADD CONSTRAINT fk_roosto_on_story FOREIGN KEY (story_id) REFERENCES stories (story_id);