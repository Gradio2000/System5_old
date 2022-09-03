alter table q_attempttests
    add consolid_test boolean,
    add test_name varchar,
    add criteria integer,
    add appoint_test_id integer,
    drop time_attempt,
    drop test_id;