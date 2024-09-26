-- Clear the tables
TRUNCATE TABLE stories CASCADE;

-- Insert data into the 'stories' table
ALTER SEQUENCE story_seq RESTART WITH 1;

-- Adjust sequences
INSERT INTO stories (story_id, story_title, story_link)
VALUES
    (nextval('story_seq'), 'Sample Story Title', 'https://example.com/story');
