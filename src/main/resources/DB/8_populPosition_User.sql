DROP TABLE IF EXISTS position_user CASCADE ;

CREATE TABLE position_user(
    position_id int,
    user_id int
);

INSERT INTO position_user(position_id, user_id)
VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7), (8, 8), (9, 9);