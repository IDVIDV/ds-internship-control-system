CREATE TABLE "role" (
    role_id SERIAL PRIMARY KEY,
    role TEXT NOT NULL UNIQUE
);

CREATE TABLE "user" (
    user_id SERIAL PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    mail TEXT NOT NULL UNIQUE,
    full_name TEXT NOT NULL,
    phone TEXT NOT NULL UNIQUE,
    telegram_id TEXT UNIQUE,
    study_status TEXT,
    university TEXT,
    faculty TEXT,
    specialization TEXT,
    course INTEGER,
    role_id INTEGER NOT NULL
)