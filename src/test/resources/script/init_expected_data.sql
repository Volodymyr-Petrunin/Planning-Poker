-- Clean up existing data
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE rooms CASCADE;
TRUNCATE TABLE stories CASCADE;
TRUNCATE TABLE votes CASCADE;
TRUNCATE TABLE events CASCADE;
TRUNCATE TABLE event_messages CASCADE;
TRUNCATE TABLE teams CASCADE;

-- Adjust sequence
ALTER SEQUENCE user_seq RESTART WITH 1;

-- Insert data into the 'users' table
INSERT INTO users (user_id, user_firstname, user_lastname, user_nickname, user_email, user_password, user_role, security_role)
VALUES (nextval('user_seq'), 'Expected Name', 'Expected Lastname', 'Expected Nickname', 'expected@email.gg', 'Expected pass', 'USER_ELECTOR', 'ROLE_USER');

-- Adjust sequence
ALTER SEQUENCE user_seq RESTART WITH 2;

-- Insert data into the 'users' table
INSERT INTO users (user_id, user_firstname, user_lastname, user_nickname, user_email, user_password, user_role, security_role)
VALUES (nextval('user_seq'), 'User', 'Creator', 'UserCreator', 'UserCreator@email.gg', 'UserCreatorPass', 'USER_PRESENTER', 'ROLE_ADMIN');

-- Adjust sequence
ALTER SEQUENCE story_seq RESTART WITH 1;

-- Insert data into the 'stories' table
INSERT INTO stories(story_id, story_title, story_link)
VALUES (nextval('story_seq'), 'Story Title', 'Story Link');

-- Adjust sequence
ALTER SEQUENCE room_seq RESTART WITH 1;

-- Insert data into the 'rooms' table
INSERT INTO rooms(room_id, room_code, room_name, room_creator, room_start_date, room_start_time, current_story_id,
                  room_is_active, room_is_voting_open, room_vote_duration)
VALUES (nextval('room_seq'), 'ABC123', 'Test Room', 2, '2000-01-01', '10:00', 1, true, false, '00:05');

-- Adjust sequence
ALTER SEQUENCE vote_seq RESTART WITH 1;

-- Insert data into the 'votes' table
INSERT INTO votes(vote_id, vote_voter, story_points, story_id)
VALUES (nextval('vote_seq'), 1, 5, 1);

-- Adjust sequence
ALTER SEQUENCE event_seq RESTART WITH 1;

-- Insert an Event
INSERT INTO events (event_id)
VALUES (nextval('event_seq'));

-- Adjust sequences
ALTER SEQUENCE event_message_seq RESTART WITH 1;

-- Insert data into the 'event_messages' table
INSERT INTO event_messages (event_message_id, event_message_user, event_message_message, event_message_timestamp)
VALUES
    (nextval('event_message_seq'), 1, 'Sample event message', '2024-01-01 00:00:00');

-- Insert connection data
INSERT INTO event_room(room_room_id, event_event_id) VALUES (1, 1);

-- Insert connection data
INSERT INTO events_messages_connection(event_id, event_message_id) VALUES (1, 1);

-- Adjust sequences
ALTER SEQUENCE team_seq RESTART WITH 1;

-- Insert data into the 'teams' table
INSERT INTO teams(team_id, team_name, team_leader_id) VALUES (nextval('team_seq'), 'Best Team', 2);

-- Insert connection data
INSERT INTO team_user_connection(team_id, user_id) VALUES (1, 1);
