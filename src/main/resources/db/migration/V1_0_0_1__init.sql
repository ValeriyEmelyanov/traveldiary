CREATE TABLE IF NOT EXISTS expense_type
(
    id      bigserial PRIMARY KEY,
    name    varchar(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS travel
(
    id              bigserial PRIMARY KEY,
    status          varchar(8)      NOT NULL DEFAULT 'Plan',
    title           varchar(150)    NOT NULL,
    start_date      date            NOT NULL,
    end_date        date,
    description     varchar(1000),
    plan_total_sum  integer         NOT NULL DEFAULT 0,
    fact_total_sum  integer         NOT NULL DEFAULT 0,
    rating          integer         NOT NULL DEFAULT 0,
    is_favorite     boolean         NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS expense_record
(
    id              bigserial PRIMARY KEY,
    travel_id       int8            NOT NULL,
    rec_no          integer         NOT NULL,
    expense_type_id int8            NOT NULL,
    comment         varchar(250),
    plan_sum        integer         NOT NULL DEFAULT 0,
    fact_sum        integer         NOT NULL DEFAULT 0,
    FOREIGN KEY (travel_id) REFERENCES travel (id),
    FOREIGN KEY (expense_type_id) REFERENCES expense_type (id)
);
