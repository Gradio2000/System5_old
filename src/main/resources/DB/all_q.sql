create table q_group_test
(
    grouptest_id integer default nextval('group_test_grouptest_id_seq'::regclass) not null
        constraint group_test_pkey
            primary key,
    name varchar not null
);

create unique index "group_test_id_groupTest_uindex"
    on q_group_test (grouptest_id);

create table q_tests
(
    test_id     integer default nextval('tests_test_id_seq'::regclass) not null
        constraint tests_pkey
            primary key,
    test_name   varchar                                                not null,
    group_id    integer
        constraint tests_group_test_id_grouptest_fk
            references q_group_test
            on update cascade on delete cascade,
    criteria    double precision,
    time        double precision,
    ques_amount integer,
    deleted     boolean,
    ques_mix    boolean default true
);

create unique index tests_test_id_uindex
    on q_tests (test_id);

