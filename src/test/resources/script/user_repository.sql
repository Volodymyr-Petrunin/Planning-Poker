-- Clear the tables and reset sequences
TRUNCATE TABLE users CASCADE;

-- Reset the sequences and fill the data
ALTER SEQUENCE user_seq RESTART WITH 1;
INSERT INTO users VALUES (nextval('user_seq'), 'First Name', 'First Lastname', 'First Nickname', 'first@email.gg', 'First pass', 'USER_PRESENTER');

ALTER SEQUENCE user_seq RESTART WITH 2;
INSERT INTO users VALUES (nextval('user_seq'), 'Second Name', 'Second Lastname', 'Second Nickname', 'second@email.gg', 'Second pass', 'USER_ELECTOR');