drop table if exists q_answers;
create table q_answers
(
    answer_id   serial
        constraint q_answers_pk
            primary key,
    answer_name varchar,
    is_right    boolean,
    question_id integer
);

create unique index q_answers_answer_id_uindex
    on q_answers (answer_id);

drop table if exists q_attempttests;
create table q_attempttests
(
    attempt_id           serial
        constraint q_attempttests_pk
            primary key,
    date_time            timestamp,
    user_id              integer,
    time_attempt         integer,
    amount_ques          integer,
    amount_false_answers integer,
    amount_true_answers  integer,
    result               double precision,
    testresult           varchar,
    test_id              integer
);

create unique index q_attempttests_attempt_id_uindex
    on q_attempttests (attempt_id);

drop table if exists q_group_test;
create table q_group_test
(
    grouptest_id serial
        constraint q_group_test_pk
            primary key,
    name         varchar
);

create unique index q_group_test_grouptest_id_uindex
    on q_group_test (grouptest_id);

drop table if exists q_questions;
create table q_questions
(
    question_id   serial
        constraint q_questions_pk
            primary key,
    question_name varchar,
    test_id       integer,
    deleted       boolean default false
);

create unique index q_questions_question_id_uindex
    on q_questions (question_id);

drop table if exists q_questions_for_attempt;
create table q_questions_for_attempt
(
    id          serial
        constraint q_questions_for_attempt_pk
            primary key,
    attempt_id  integer,
    question_id integer
);

create unique index q_questions_for_attempt_id_uindex
    on q_questions_for_attempt (id);

drop table if exists q_result_test;
create table q_result_test
(
    resulttest_id serial
        constraint q_result_test_pk
            primary key,
    attempt_id    integer,
    question_id   integer,
    answer_id     integer
);

create unique index q_result_test_resulttest_id_uindex
    on q_result_test (resulttest_id);

drop table if exists q_tests;
create table q_tests
(
    test_id     serial
        constraint q_tests_pk
            primary key,
    test_name   varchar,
    criteria    double precision,
    time        double precision,
    ques_amount integer,
    ques_mix    boolean,
    deleted     boolean,
    group_id    integer
);

create unique index q_tests_test_id_uindex
    on q_tests (test_id);

