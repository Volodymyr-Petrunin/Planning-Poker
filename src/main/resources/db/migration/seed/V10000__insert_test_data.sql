INSERT INTO users(user_id, user_firstname, user_lastname, user_nickname, user_email, user_password, security_role)
VALUES
    (1, 'admin', 'adminovich', 'The Best Admin', 'admin@example.com',
        '$2a$12$4Wyvz9PIGJIPeQAORXaL0.Y/2BHgI3MgEE9jMbNpIMZSOEnnFjf4K', 'ROLE_ADMIN'),
    (2, 'user', 'userovich', 'Perfect User', 'user@example.com',
        '$2a$12$Og7zQmypiTHLcuemmFIrLud2exugyLeM6zCmKe1JwVJiArBWwnXP6', 'ROLE_USER'),
    (3, 'user2', 'lastname', 'Awesome User', 'user2@example.com',
         '$2a$12$eUFqydRnabSYIefe4gAWEeAaxH.W.ipU8dVRh/XS68hH8ZrP0BR0C', 'ROLE_USER'),
    (4, 'user3', 'data', 'Default User', 'user3@example.com',
         '$2a$12$lpjlx4eiX5MIb746P4d2DeNbB3wWE2nOcMrd8PePidViLfZ2o2X02', 'ROLE_USER');

-- Set the starting value of the sequence for the users table.
-- The value 54 is chosen to avoid conflicts with existing IDs,
-- as Hibernate uses allocationSize = 50. This means Hibernate
-- reserves ranges of IDs in batches of 50.
ALTER SEQUENCE user_seq RESTART WITH 54;

INSERT INTO stories(story_id, story_title, story_link)
VALUES
    (1, 'Handle Sequence In Spring Boot Tests', 'https://stackoverflow.com/questions/79021656/how-to-handle-sequences-in-spring-boot-tests-with-sql-scripts'),
    (2, 'If you launch the project write to me', 'https://www.linkedin.com/in/volodymyr-petrunin-0316302b5/'),
    (3, 'Example Issue On GitHub', 'https://github.com/Volodymyr-Petrunin/Planning-Poker/issues/48'),
    (4, 'Move To Microservices', 'https://www.youtube.com/watch?v=rckfN7xFig0'),
    (5, 'Example Delete Issue', 'https://www.google.se/?hl=sv');

-- Set the starting value of the sequence for the stories table.
-- The value 15 is chosen to avoid conflicts with existing IDs,
-- as Hibernate uses allocationSize = 10. This means Hibernate
-- reserves ranges of IDs in batches of 10.
ALTER SEQUENCE story_seq RESTART WITH 15;

INSERT INTO rooms(room_id, room_code, room_name, room_creator, room_start_time, current_story_id, room_is_active, room_is_voting_open, room_start_date, room_vote_duration)
VALUES
    (1, 'firstRoom', 'First Room', 1, '20:00', null, true, false, now(), 900000000000),
    (2, 'secondRoom', 'Second Room', 2, '20:00', null, true, false, now(), 900000000000);

-- Set the starting value of the sequence for the rooms table.
-- The value 12 is chosen to avoid conflicts with existing IDs,
-- as Hibernate uses allocationSize = 10. This means Hibernate
-- reserves ranges of IDs in batches of 10.
ALTER SEQUENCE room_seq RESTART WITH 12;

INSERT INTO room_story (room_id, story_id)
VALUES
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
    (2, 1), (2, 2), (2, 3), (2, 4), (2, 5);

INSERT INTO room_user_roles (id, user_id, room_id, role)
VALUES
    (1, 1, 1, 'USER_PRESENTER'), (2, 1, 2, 'USER_ELECTOR'),
    (3, 2, 1, 'USER_ELECTOR'), (4, 2, 2, 'USER_PRESENTER'),
    (5, 3, 1, 'USER_SPECTATOR'), (6, 3, 2, 'USER_ELECTOR'),
    (7, 4, 1, 'USER_SPECTATOR'), (8, 4, 2, 'USER_SPECTATOR');

-- Set the starting value of the sequence for the rooms table.
-- The value 18 is chosen to avoid conflicts with existing IDs,
-- as Hibernate uses allocationSize = 10. This means Hibernate
-- reserves ranges of IDs in batches of 10.
ALTER SEQUENCE room_user_role_seq RESTART WITH 18;