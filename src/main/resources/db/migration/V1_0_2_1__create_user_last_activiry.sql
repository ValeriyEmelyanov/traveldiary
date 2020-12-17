CREATE TABLE IF NOT EXISTS user_last_activity
(
    user_id       bigserial PRIMARY KEY,
    last_activity timestamp    NOT NULL,
    description   varchar(150) NOT NULL
);