ALTER TABLE room_story DROP CONSTRAINT IF EXISTS fk_roosto_on_room;
ALTER TABLE room_story DROP CONSTRAINT IF EXISTS fk_roosto_on_story;


ALTER TABLE room_story
    ADD CONSTRAINT fk_roosto_on_room FOREIGN KEY (room_id)
        REFERENCES rooms (room_id) ON DELETE CASCADE;

ALTER TABLE room_story
    ADD CONSTRAINT fk_roosto_on_story FOREIGN KEY (story_id)
        REFERENCES stories (story_id) ON DELETE CASCADE;