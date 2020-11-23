CREATE TABLE IF NOT EXISTS users
(
    id          bigserial PRIMARY KEY,
    username    varchar(50)     NOT NULL,
    password    varchar(255)    NOT NULL,
    created     date            NOT NULL,
    enable      boolean         NOT NULL
);

CREATE UNIQUE INDEX users_username_idx ON users (username);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id     int8            NOT NULL,
    role        varchar(20)     NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_roles_role_unique UNIQUE (user_id, role)
);

INSERT INTO users (username, password, created, enable)
VALUES ('admin', '$2y$12$Vuf94vmYpJGTd8jMoxnpqOixjRBrYObt0H6u8O0sEZ7RIe/2VE2G6', now(), true)