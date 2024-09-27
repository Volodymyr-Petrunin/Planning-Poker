-- Drop existing foreign key constraints
ALTER TABLE events_event_messages DROP CONSTRAINT IF EXISTS fk_eveevemes_on_event;
ALTER TABLE event_room DROP CONSTRAINT IF EXISTS fk_everoo_on_event;

-- Create sequence for events if it doesn't exist
CREATE SEQUENCE IF NOT EXISTS event_seq START WITH 1 INCREMENT BY 10;

-- Modify tables: add new columns
ALTER TABLE event_room ADD COLUMN IF NOT EXISTS event_event_id BIGINT;
ALTER TABLE events ADD COLUMN IF NOT EXISTS event_id BIGINT;
ALTER TABLE events_event_messages ADD COLUMN IF NOT EXISTS events_event_id BIGINT NOT NULL;

-- Drop unnecessary columns
ALTER TABLE event_room DROP COLUMN IF EXISTS events_id;
ALTER TABLE events DROP COLUMN IF EXISTS id;
ALTER TABLE events_event_messages DROP COLUMN IF EXISTS events_id;

-- Change data type for room_vote_duration if it exists
ALTER TABLE rooms ALTER COLUMN room_vote_duration TYPE time WITHOUT TIME ZONE;

-- Ensure the event_id column is unique and add primary key
ALTER TABLE events ADD CONSTRAINT pk_events PRIMARY KEY (event_id);

-- Add unique constraints
ALTER TABLE event_room ADD CONSTRAINT uc_event_room_event_event UNIQUE (event_event_id);
ALTER TABLE events_event_messages ADD CONSTRAINT uc_events_event_messages_event UNIQUE (events_event_id, event_messages_event_message_id);

-- Add foreign key constraints
ALTER TABLE events_event_messages ADD CONSTRAINT fk_eveevemes_on_event FOREIGN KEY (events_event_id) REFERENCES events (event_id);
ALTER TABLE event_room ADD CONSTRAINT fk_everoo_on_event FOREIGN KEY (event_event_id) REFERENCES events (event_id);

-- Add primary key constraints for event_room
ALTER TABLE event_room ADD CONSTRAINT pk_event_room PRIMARY KEY (event_event_id);

-- Remove old constraint
ALTER TABLE events_event_messages DROP CONSTRAINT IF EXISTS uc_events_event_messages_eventmessages_event_message;