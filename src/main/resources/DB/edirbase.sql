alter table q_attempttests
    add consolid_test boolean,
    add test_name varchar,
    add criteria integer,
    drop time_attempt,
    drop test_id;


alter table q_appoint_tests
    add test_name varchar,
    add criteria integer,
    add amount_ques integer,
    drop test_id;

drop table if exists q_appoint_test_amount;
create table q_appoint_test_amount
(
    id          serial
        constraint q_appoint_test_amount_pk
            primary key,
    appoint_id  integer not null,
    test_id     integer not null,
    ques_amount integer not null
);

create unique index q_appoint_test_amount_id_uindex
    on q_appoint_test_amount (id);

