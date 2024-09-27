-- Clean up existing data
TRUNCATE TABLE events CASCADE;
TRUNCATE TABLE event_messages CASCADE;
TRUNCATE TABLE rooms CASCADE;
TRUNCATE TABLE users CASCADE;

-- Adjust sequences
ALTER SEQUENCE room_seq RESTART WITH 1;

-- Insert a Room
INSERT INTO rooms (room_id, room_code, room_name, room_start_date, room_start_time, room_vote_duration, room_is_active, room_is_voting_open)
VALUES (1, 'Test Room Code', 'Test Room', '2024-01-01 00:00:00.0', '12:00', '1:00', TRUE, TRUE);

-- Adjust sequences
ALTER SEQUENCE user_seq RESTART WITH 1;

-- Insert a User (if needed)
INSERT INTO users (user_id, user_firstname, user_lastname, user_nickname, user_email, user_password, user_role)
VALUES (1, 'User Name', 'User Lastname', 'User Nickname', 'user@email.com', 'user_pass', 'USER_SPECTATOR');

-- Adjust sequences
ALTER SEQUENCE event_message_seq RESTART WITH 1;

-- Insert an EventMessage
INSERT INTO event_messages (event_message_id, event_message_user, event_message_message, event_message_timestamp)
VALUES (nextval('event_message_seq'), 1, 'Sample event message', '2024-01-01 00:00:00');

-- Adjust sequences
ALTER SEQUENCE event_seq RESTART WITH 1;

-- Insert an Event
INSERT INTO events (event_id)
VALUES (nextval('event_seq'));

-- Insert connection data
INSERT INTO event_room(room_room_id, event_event_id)
VALUES (1, 1);

-- Insert connection data
INSERT INTO events_event_messages(event_messages_event_message_id, events_event_id)
VALUES (1, 1);