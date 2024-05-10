CREATE TABLE archive.user
(
    user_id        INTEGER PRIMARY KEY,
    username       TEXT NOT NULL UNIQUE,
    mail           TEXT NOT NULL UNIQUE,
    full_name      TEXT NOT NULL,
    phone          TEXT NOT NULL UNIQUE,
    telegram_id    TEXT UNIQUE,
    study_status   TEXT,
    university     TEXT,
    faculty        TEXT,
    specialization TEXT,
    course         INTEGER
);

CREATE TABLE archive.internship
(
    internship_id   INTEGER PRIMARY KEY,
    internship_name TEXT      NOT NULL,
    description     TEXT,
    start_date      TIMESTAMP NOT NULL,
    sign_end_date   TIMESTAMP NOT NULL
);

CREATE TABLE archive.user_internship
(
    user_internship_id INTEGER PRIMARY KEY,
    user_id            INTEGER NOT NULL,
    internship_id      INTEGER NOT NULL,
    CONSTRAINT user_internship_user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES archive.user (user_id)
            ON DELETE CASCADE,
    CONSTRAINT archive_user_internship_internship_id_fk
        FOREIGN KEY (internship_id)
            REFERENCES archive.internship (internship_id)
            ON DELETE CASCADE,
    CONSTRAINT archive_user_internship_user_and_internship_id_unique
        UNIQUE (user_id, internship_id)
);

CREATE TABLE archive.performance (
    performance_id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    internship_id INTEGER NOT NULL,
    fork_url TEXT NOT NULL,
    accepted BOOLEAN NOT NULL
);