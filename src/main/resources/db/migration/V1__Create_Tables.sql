CREATE TABLE university
(
    university_id SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE faculty
(
    faculty_id    SERIAL PRIMARY KEY,
    university_id INTEGER      NOT NULL,
    name          VARCHAR(255) NOT NULL UNIQUE,
    FOREIGN KEY (university_id) REFERENCES university (university_id)
);

CREATE TABLE student_group
(
    group_id   SERIAL PRIMARY KEY,
    faculty_id INTEGER      NOT NULL,
    name       VARCHAR(255) NOT NULL UNIQUE,
    FOREIGN KEY (faculty_id) REFERENCES faculty (faculty_id)
);

CREATE TABLE course
(
    course_id  SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    faculty_id INTEGER      NOT NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculty (faculty_id)
);

CREATE TABLE users
(
    user_id    SERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(20)  NOT NULL,
    faculty_id INTEGER      NOT NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculty (faculty_id)
);

CREATE TABLE students
(
    user_id  INTEGER PRIMARY KEY,
    group_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (group_id) REFERENCES student_group (group_id)
);

CREATE TABLE professors
(
    user_id      INTEGER,
    professor_id SERIAL PRIMARY KEY,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE schedule
(
    schedule_id  SERIAL PRIMARY KEY,
    group_id INTEGER   NOT NULL,
    professor_id INTEGER NOT NULL,
    course_id    INTEGER   NOT NULL,
    start_time   TIMESTAMP NOT NULL,
    end_time     TIMESTAMP NOT NULL,
    date         DATE      NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course (course_id),
    FOREIGN KEY (group_id) REFERENCES student_group (group_id),
    FOREIGN KEY (professor_id) REFERENCES professors (professor_id)

);
