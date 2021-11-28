CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE answer_sheets
(
    id                BIGINT NOT NULL,
    chosen_answer     TEXT,
    chosen_time       TIMESTAMP WITHOUT TIME ZONE,
    grade             DOUBLE PRECISION,
    testing_result_id BIGINT,
    question_id       BIGINT,
    CONSTRAINT pk_answersheets PRIMARY KEY (id)
);

CREATE TABLE group_details
(
    group_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL,
    CONSTRAINT pk_groupdetails PRIMARY KEY (group_id, user_id)
);

CREATE TABLE groups
(
    id           BIGINT             NOT NULL,
    name         VARCHAR(255)       NOT NULL,
    status       SMALLINT DEFAULT 1 NOT NULL,
    is_deleted   SMALLINT DEFAULT 0 NOT NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    lecturer_id  BIGINT,
    subject_id   BIGINT,
    CONSTRAINT pk_groups PRIMARY KEY (id)
);

CREATE TABLE options
(
    id             BIGINT NOT NULL,
    option_content TEXT,
    correct        BOOLEAN,
    question_id    BIGINT,
    CONSTRAINT pk_options PRIMARY KEY (id)
);

CREATE TABLE question_types
(
    id        BIGINT NOT NULL,
    type_name VARCHAR(20),
    CONSTRAINT pk_questiontypes PRIMARY KEY (id)
);

CREATE TABLE questions
(
    id               BIGINT             NOT NULL,
    question         TEXT,
    mark             DOUBLE PRECISION,
    is_shuffle       BOOLEAN,
    status           SMALLINT DEFAULT 1 NOT NULL,
    created_date     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_date     TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    is_deleted       SMALLINT DEFAULT 0 NOT NULL,
    question_type_id BIGINT,
    subject_id       BIGINT,
    CONSTRAINT pk_questions PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   BIGINT NOT NULL,
    name VARCHAR(20),
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE subjects
(
    id          BIGINT             NOT NULL,
    course_name VARCHAR(255)       NOT NULL,
    status      SMALLINT DEFAULT 1 NOT NULL,
    is_deleted  SMALLINT DEFAULT 0 NOT NULL,
    CONSTRAINT pk_subjects PRIMARY KEY (id)
);

CREATE TABLE testing_details
(
    id          BIGINT NOT NULL,
    test_order  INTEGER,
    test_code   VARCHAR(255),
    test_id     BIGINT,
    question_id BIGINT,
    CONSTRAINT pk_testingdetails PRIMARY KEY (id)
);

CREATE TABLE testing_results
(
    id         BIGINT             NOT NULL,
    grade      DOUBLE PRECISION,
    status     SMALLINT DEFAULT 1 NOT NULL,
    student_id BIGINT,
    test_id    BIGINT,
    CONSTRAINT pk_testingresults PRIMARY KEY (id)
);

CREATE TABLE tests
(
    id             BIGINT             NOT NULL,
    title          VARCHAR(255),
    description    TEXT,
    start_date     TIMESTAMP WITHOUT TIME ZONE,
    end_date       TIMESTAMP WITHOUT TIME ZONE,
    doing_duration INTEGER,
    code           INTEGER,
    start_time     TIMESTAMP WITHOUT TIME ZONE,
    created_date   TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_date   TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    status         SMALLINT DEFAULT 1 NOT NULL,
    subject_id     BIGINT,
    CONSTRAINT pk_tests PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_userroles PRIMARY KEY (role_id, user_id)
);

CREATE TABLE users
(
    id            BIGINT             NOT NULL,
    username      VARCHAR(255)       NOT NULL,
    password      VARCHAR(255)       NOT NULL,
    email         VARCHAR(255),
    full_name     VARCHAR(255),
    phone         VARCHAR(255),
    address       VARCHAR(255),
    status        SMALLINT DEFAULT 1 NOT NULL,
    date_of_birth date,
    gender        CHAR,
    created_date  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_date  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    is_deleted    SMALLINT DEFAULT 0 NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE question_types
    ADD CONSTRAINT uc_questiontypes_typename UNIQUE (type_name);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE answer_sheets
    ADD CONSTRAINT FK_ANSWERSHEETS_ON_QUESTION FOREIGN KEY (question_id) REFERENCES questions (id);

ALTER TABLE answer_sheets
    ADD CONSTRAINT FK_ANSWERSHEETS_ON_TESTINGRESULT FOREIGN KEY (testing_result_id) REFERENCES testing_results (id);

ALTER TABLE groups
    ADD CONSTRAINT FK_GROUPS_ON_LECTURER FOREIGN KEY (lecturer_id) REFERENCES users (id);

ALTER TABLE groups
    ADD CONSTRAINT FK_GROUPS_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES subjects (id);

ALTER TABLE options
    ADD CONSTRAINT FK_OPTIONS_ON_QUESTION FOREIGN KEY (question_id) REFERENCES questions (id);

ALTER TABLE questions
    ADD CONSTRAINT FK_QUESTIONS_ON_QUESTIONTYPE FOREIGN KEY (question_type_id) REFERENCES question_types (id);

ALTER TABLE questions
    ADD CONSTRAINT FK_QUESTIONS_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES subjects (id);

ALTER TABLE testing_details
    ADD CONSTRAINT FK_TESTINGDETAILS_ON_QUESTION FOREIGN KEY (question_id) REFERENCES questions (id);

ALTER TABLE testing_details
    ADD CONSTRAINT FK_TESTINGDETAILS_ON_TEST FOREIGN KEY (test_id) REFERENCES tests (id);

ALTER TABLE testing_results
    ADD CONSTRAINT FK_TESTINGRESULTS_ON_STUDENT FOREIGN KEY (student_id) REFERENCES users (id);

ALTER TABLE testing_results
    ADD CONSTRAINT FK_TESTINGRESULTS_ON_TEST FOREIGN KEY (test_id) REFERENCES tests (id);

ALTER TABLE tests
    ADD CONSTRAINT FK_TESTS_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES subjects (id);

ALTER TABLE group_details
    ADD CONSTRAINT fk_grodet_on_group FOREIGN KEY (group_id) REFERENCES groups (id);

ALTER TABLE group_details
    ADD CONSTRAINT fk_grodet_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);