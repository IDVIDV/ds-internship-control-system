CREATE TABLE internship
(
    internship_id   SERIAL PRIMARY KEY,
    internship_name TEXT      NOT NULL,
    description     TEXT,
    start_date      TIMESTAMP NOT NULL,
    sign_end_date   TIMESTAMP NOT NULL,
    status          TEXT      NOT NULL
);

CREATE TABLE user_internship
(
    user_internship_id SERIAL PRIMARY KEY,
    user_id            INTEGER NOT NULL,
    internship_id      INTEGER NOT NULL,
    status             TEXT    NOT NULL,
    CONSTRAINT user_internship_user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES "user" (user_id)
            ON DELETE CASCADE,
    CONSTRAINT user_internship_internship_id_fk
        FOREIGN KEY (internship_id)
            REFERENCES internship (internship_id)
            ON DELETE CASCADE,
    CONSTRAINT user_internship_user_and_internship_id_unique
        UNIQUE (user_id, internship_id)
);

CREATE TABLE "message"
(
    message_id      SERIAL PRIMARY KEY,
    send_date       TIMESTAMP NOT NULL,
    message_content TEXT,
    sender_id       INTEGER,
    receiver_id     INTEGER   NOT NULL,
    CONSTRAINT message_sender_id_fk
        FOREIGN KEY (sender_id)
            REFERENCES "user" (user_id)
            ON DELETE SET NULL,
    CONSTRAINT message_receiver_id_faker
        FOREIGN KEY (receiver_id)
            REFERENCES "user" (user_id)
            ON DELETE CASCADE
);

CREATE TABLE lesson
(
    lesson_id     SERIAL PRIMARY KEY,
    internship_id INTEGER NOT NULL,
    lesson_name   TEXT    NOT NULL,
    description   TEXT,
    CONSTRAINT lesson_internship_id_fk
        FOREIGN KEY (internship_id)
            REFERENCES internship (internship_id)
            ON DELETE CASCADE
);

CREATE TABLE task
(
    task_id     SERIAL PRIMARY KEY,
    lesson_id   INTEGER NOT NULL,
    task_name   TEXT    NOT NULL,
    description TEXT,
    CONSTRAINT task_lesson_id_fk
        FOREIGN KEY (lesson_id)
            REFERENCES lesson (lesson_id)
            ON DELETE CASCADE
);

CREATE TABLE task_fork
(
    task_fork_id SERIAL PRIMARY KEY,
    task_id      INTEGER NOT NULL,
    user_id      INTEGER NOT NULL,
    accepted     BOOLEAN NOT NULL,
    CONSTRAINT task_fork_task_id_fk
        FOREIGN KEY (task_id)
            REFERENCES task (task_id)
            ON DELETE CASCADE,
    CONSTRAINT task_fork_user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES "user" (user_id)
            ON DELETE CASCADE
);

CREATE TABLE "commit"
(
    commit_id    SERIAL PRIMARY KEY,
    task_fork_id INTEGER   NOT NULL,
    author       TEXT,
    commit_date  TIMESTAMP NOT NULL,
    url          TEXT      NOT NULL,
    CONSTRAINT commit_task_fork_id_fk
        FOREIGN KEY (task_fork_id)
            REFERENCES task_fork (task_fork_id)
            ON DELETE CASCADE
);

CREATE TABLE "comment"
(
    comment_id      SERIAL PRIMARY KEY,
    user_id         INTEGER NOT NULL,
    commit_id       INTEGER NOT NULL,
    comment_content TEXT,
    CONSTRAINT comment_user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES "user" (user_id)
            ON DELETE CASCADE,
    CONSTRAINT comment_commit_id_fk
        FOREIGN KEY (commit_id)
            REFERENCES "commit" (commit_id)
            ON DELETE CASCADE
);