-- Drop the existing foreign key constraint FK_ROOMS_ON_CURRENT_STORY from the 'rooms' table if it exists
ALTER TABLE rooms
    DROP CONSTRAINT IF EXISTS FK_ROOMS_ON_CURRENT_STORY;

-- Add a new foreign key constraint FK_ROOMS_ON_CURRENT_STORY on 'current_story_id' column
-- This constraint references the 'stories' table on 'story_id' column with ON DELETE CASCADE
-- This ensures that when a referenced story is deleted, the related rows in 'rooms' are also deleted
ALTER TABLE rooms
    ADD CONSTRAINT FK_ROOMS_ON_CURRENT_STORY FOREIGN KEY (current_story_id)
        REFERENCES stories (story_id) ON DELETE CASCADE;

-- Drop the existing foreign key constraint FK_EVEROO_ON_ROOM from the 'event_room' table if it exists
ALTER TABLE event_room
    DROP CONSTRAINT IF EXISTS FK_EVEROO_ON_ROOM;

-- Add a new foreign key constraint FK_EVENT_ROOM_ON_ROOM on 'room_room_id' column
-- This constraint references the 'rooms' table on 'room_id' column with ON DELETE CASCADE
-- This ensures that when a referenced room is deleted, the related rows in 'event_room' are also deleted
ALTER TABLE event_room
    ADD CONSTRAINT FK_EVENT_ROOM_ON_ROOM FOREIGN KEY (room_room_id)
        REFERENCES rooms (room_id) ON DELETE CASCADE;

-- Drop the existing foreign key constraint fk_votes_on_story from the 'votes' table if it exists
ALTER TABLE votes
    DROP CONSTRAINT IF EXISTS fk_votes_on_story;

-- Add a new foreign key constraint FK_VOTES_ON_STORY on 'story_id' column
-- This constraint references the 'stories' table on 'story_id' column with ON DELETE CASCADE
-- This ensures that when a referenced story is deleted, the related rows in 'votes' are also deleted
ALTER TABLE votes
    ADD CONSTRAINT FK_VOTES_ON_STORY FOREIGN KEY (story_id)
        REFERENCES stories (story_id) ON DELETE CASCADE;

-- Drop the existing foreign key constraint FK_EVEROO_ON_EVENT from the 'event_room' table if it exists
ALTER TABLE event_room
    DROP CONSTRAINT IF EXISTS FK_EVEROO_ON_EVENT;

-- Add a new foreign key constraint FK_EVENT_ROOM_ON_EVENT on 'room_room_id' column
-- This constraint references the 'rooms' table on 'room_id' column with ON DELETE CASCADE
-- Ensures that when the referenced room is deleted, the related rows in 'event_room' are deleted as well
ALTER TABLE event_room
    ADD CONSTRAINT FK_EVENT_ROOM_ON_EVENT FOREIGN KEY (room_room_id)
        REFERENCES rooms (room_id) ON DELETE CASCADE;
