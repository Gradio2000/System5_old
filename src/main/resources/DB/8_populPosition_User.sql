DROP TABLE IF EXISTS system5 CASCADE ;

CREATE TABLE position_user(
    position_id int,
    user_id int
);

INSERT INTO position_user(position_id, user_id)
VALUES (1, 1), (9, 2);