-- Clear the tables
TRUNCATE TABLE votes CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE stories CASCADE;

-- Reset the sequences and Insert data for Users
ALTER SEQUENCE vote_seq RESTART WITH 1;
INSERT INTO users (user_id, user_firstname, user_lastname, user_nickname, user_email, user_password, user_role)
VALUES
    (nextval('user_seq'), 'Voter Name', 'Voter Lastname', 'Voter Nickname', 'voter@email.com', 'voter_pass', 'USER_ELECTOR');

-- Reset the sequences and Insert data for Stories
ALTER SEQUENCE story_seq RESTART WITH 1;
INSERT INTO stories (story_id, story_title, story_link)
VALUES
    (nextval('story_seq'), 'Story Title', 'Story Description');

-- Reset the sequences and Insert data for Votes
ALTER SEQUENCE user_seq RESTART WITH 1;
INSERT INTO votes (vote_id, vote_voter, story_points, story_id)
VALUES
    (nextval('vote_seq'), 1, 5, 1);