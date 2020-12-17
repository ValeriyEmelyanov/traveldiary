DROP TABLE IF EXISTS user_last_activity;

CREATE TABLE IF NOT EXISTS user_last_activity
(
    id            bigserial PRIMARY KEY,
    user_id       int8         NOT NULL ,
    last_activity timestamp    NOT NULL,
    description   varchar(150) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT user_last_activity_unique UNIQUE (user_id)
);