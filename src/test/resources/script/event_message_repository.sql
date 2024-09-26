-- Truncate the tables before inserting data
TRUNCATE TABLE event_messages CASCADE;
TRUNCATE TABLE users CASCADE;

-- Adjust sequence
ALTER SEQUENCE user_seq RESTART WITH 1;

INSERT INTO users(user_id, user_firstname, user_lastname, user_nickname, user_email, user_password, user_role)
VALUES
    (nextval('user_seq'), 'User Name', 'User Lastname','User Nickname', 'user@email.com', 'user_pass', 'USER_SPECTATOR');

-- Adjust sequence
ALTER SEQUENCE event_message_seq RESTART WITH 1;

-- Insert data into the 'event_messages' table
INSERT INTO event_messages (event_message_id, event_message_user, event_message_message, event_message_timestamp)
VALUES
    (nextval('event_message_seq'), 1, 'Sample event message', '2024-01-01 00:00:00');
