INSERT INTO events(event_id) VALUES (1), (2);

ALTER SEQUENCE event_seq RESTART WITH 12;

INSERT INTO event_room(room_room_id, event_event_id) VALUES (1, 1), (2, 2);