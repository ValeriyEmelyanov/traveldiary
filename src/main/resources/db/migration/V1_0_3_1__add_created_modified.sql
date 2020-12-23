ALTER TABLE users
    ADD COLUMN modified Timestamp;

ALTER TABLE travel
    ADD COLUMN created Timestamp,
    ADD COLUMN modified timestamp;