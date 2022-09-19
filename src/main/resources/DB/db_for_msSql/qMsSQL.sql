drop table if exists q_answers;
create table q_answers
(
    answer_id   int IDENTITY (1, 1)
            primary key,
    answer_name nvarchar(255),
    is_right    bit,
    question_id integer
);

drop table if exists q_attempttests;
create table q_attempttests
(
    attempt_id           int IDENTITY (1, 1) primary key,
    date_time            timestamp,
    user_id              integer,
    time_attempt         integer,
    amount_ques          integer,
    amount_false_answers integer,
    amount_true_answers  integer,
    result               double precision,
    testresult           nvarchar(255),
    test_id              integer,
    appoint_test_id      integer
);


drop table if exists q_group_test;
create table q_group_test
(
    grouptest_id int IDENTITY (1, 1) primary key,
    name         nvarchar(255)
);

drop table if exists q_questions;
create table q_questions
(
    question_id   int IDENTITY (1, 1) primary key,
    question_name nvarchar(255),
    test_id       integer,
    deleted       bit
);

drop table if exists q_questions_for_attempt;
create table q_questions_for_attempt
(
    id          int IDENTITY (1, 1) primary key,
    attempt_id  integer,
    question_id integer
);

drop table if exists q_result_test;
create table q_result_test
(
    resulttest_id int IDENTITY (1, 1) primary key,
    attempt_id    integer,
    question_id   integer,
    answer_id     integer
);

drop table if exists q_tests;
create table q_tests
(
    test_id     int IDENTITY (1, 1) primary key,
    test_name   nvarchar(255),
    criteria    double precision,
    time        double precision,
    ques_amount integer,
    ques_mix    bit,
    deleted     bit,
    group_id    integer
);

drop table if exists q_appoint_tests;

create table q_appoint_tests
(
    id_appoint_test int IDENTITY (1, 1) primary key,
    user_id         integer,
    test_id         integer,
    finished        bit,
    base            nvarchar(255),
    attempt_test_id int,
    eko bit
);


drop table if exists q_false_users_answers;
create table q_false_users_answers
(
    id          int IDENTITY (1, 1) primary key,
    question_id integer,
    attempt_id  integer
);

drop table if exists q_appoint_test_amount;
create table q_appoint_test_amount
(
    id          int IDENTITY (1, 1) primary key,
    appoint_id  integer not null,
    test_id     integer not null,
    ques_amount integer not null
);



