ALTER TABLE travel
    ADD COLUMN user_id int8,
    ADD FOREIGN KEY (user_id) REFERENCES users (id);