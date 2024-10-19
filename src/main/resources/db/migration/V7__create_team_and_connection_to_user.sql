CREATE SEQUENCE IF NOT EXISTS team_seq START WITH 1 INCREMENT BY 10;

CREATE TABLE team_user_connection
(
    team_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

CREATE TABLE teams
(
    team_id        BIGINT NOT NULL,
    team_name      VARCHAR(255),
    team_leader_id BIGINT NOT NULL,
    CONSTRAINT pk_teams PRIMARY KEY (team_id)
);

ALTER TABLE teams
    ADD CONSTRAINT FK_TEAMS_ON_TEAM_LEADER FOREIGN KEY (team_leader_id)
        REFERENCES users (user_id) ON DELETE CASCADE;

ALTER TABLE team_user_connection
    ADD CONSTRAINT FK_TEAM_USER_CONNECTIONS_TEAM FOREIGN KEY (team_id)
        REFERENCES teams (team_id) ON DELETE CASCADE;

ALTER TABLE team_user_connection
    ADD CONSTRAINT FK_TEAM_USER_CONNECTIONS_USER FOREIGN KEY (user_id)
        REFERENCES users (user_id) ON DELETE CASCADE;