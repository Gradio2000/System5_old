drop table if exists commander_employee;

CREATE TABLE commander_employee(
    commander_position_id int,
    position_id int
);

INSERT INTO commander_employee(commander_position_id, position_id)
VALUES (1, 2), (1, 3), (1, 4), (3, 5), (3, 6), (2, 7), (2, 8)