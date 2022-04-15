DROP TABLE IF EXISTS positions cascade;

CREATE TABLE positions
(
    position_id serial not null primary key,
    position VARCHAR not null,
    division_id int not null
);
create unique index positions_position_id_uindex
    on positions ("position_id");

INSERT INTO positions (position, division_id) VALUES ('Администратор', 0);
INSERT INTO positions (position, division_id) VALUES ('Начальник банка', 1);
INSERT INTO positions (position, division_id) VALUES ('Заместитель начальника банка', 1);
INSERT INTO positions (position, division_id) VALUES ('Заместитель начальника банка (ОР)', 1);
INSERT INTO positions (position, division_id) VALUES ('Начальник кассы', 3);
INSERT INTO positions (position, division_id) VALUES ('Ведущий экономист',2);
INSERT INTO positions (position, division_id) VALUES ('Экономист 1 категории', 2);
INSERT INTO positions (position, division_id) VALUES ('Ведущий инженер',4);
INSERT INTO positions (position, division_id) VALUES ('Водитель автомобиля',5);
