DROP TABLE IF EXISTS positions cascade;

CREATE TABLE positions
(
    position_id serial not null primary key,
    position VARCHAR not null
);
create unique index positions_position_id_uindex
    on positions ("position_id");

INSERT INTO positions (position) VALUES ('Администратор');
INSERT INTO positions (position) VALUES ('Начальник банка');
INSERT INTO positions (position) VALUES ('Заместитель начальника банка');
INSERT INTO positions (position) VALUES ('Заместитель начальника банка (ОР)');
INSERT INTO positions (position) VALUES ('Начальник кассы');
INSERT INTO positions (position) VALUES ('Ведущий экономист');
INSERT INTO positions (position) VALUES ('Экономист 1 категории');
INSERT INTO positions (position) VALUES ('Ведущий инженер');
INSERT INTO positions (position) VALUES ('Водитель автомобиля');
