-- Drop existing foreign key constraints on the old events_event_messages table
ALTER TABLE events_event_messages
    DROP CONSTRAINT IF EXISTS fk_eveevemes_on_event;

ALTER TABLE events_event_messages
    DROP CONSTRAINT IF EXISTS fk_eveevemes_on_event_message;

-- Create the new events_messages_connection table with necessary fields
CREATE TABLE events_messages_connection
(
    event_id         BIGINT NOT NULL,
    event_message_id BIGINT NOT NULL
);

-- Add unique constraint to prevent duplicate event-message pairs
ALTER TABLE events_messages_connection
    ADD CONSTRAINT uc_events_messages_connection UNIQUE (event_id, event_message_id);

-- Add foreign key constraint on event_id referring to events table
ALTER TABLE events_messages_connection
    ADD CONSTRAINT fk_events_messages_event FOREIGN KEY (event_id)
        REFERENCES events (event_id)
        ON DELETE CASCADE;

-- Add foreign key constraint on event_message_id referring to event_messages table
ALTER TABLE events_messages_connection
    ADD CONSTRAINT fk_events_messages_event_message FOREIGN KEY (event_message_id)
        REFERENCES event_messages (event_message_id)
        ON DELETE CASCADE;

-- Safely drop the old table and cascade any remaining references
DROP TABLE IF EXISTS events_event_messages CASCADE;