CREATE SEQUENCE IF NOT EXISTS room_user_role_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE room_user_roles
(
    id      BIGINT      NOT NULL,
    user_id BIGINT      NOT NULL,
    room_id BIGINT      NOT NULL,
    role    VARCHAR(20) NOT NULL,
    CONSTRAINT pk_room_user_roles PRIMARY KEY (id)
);

ALTER TABLE rooms
    ADD CONSTRAINT uc_rooms_room_creator UNIQUE (room_creator);

ALTER TABLE room_user_roles
    ADD CONSTRAINT FK_ROOM_USER_ROLES_ON_ROOM FOREIGN KEY (room_id)
        REFERENCES rooms (room_id) ON DELETE CASCADE;

ALTER TABLE room_user_roles
    ADD CONSTRAINT FK_ROOM_USER_ROLES_ON_USER FOREIGN KEY (user_id)
        REFERENCES users (user_id) ON DELETE CASCADE;

ALTER TABLE users
    DROP COLUMN user_role;