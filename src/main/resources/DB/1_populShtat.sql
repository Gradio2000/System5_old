DROP TABLE IF EXISTS shtat cascade;

CREATE TABLE shtat(
    shtat_id serial primary key not null,
    division_id int not null,
    user_id int
);

INSERT INTO shtat (division_id, user_id)
VALUES (1, 1);