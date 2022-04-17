DROP TABLE IF EXISTS divisions cascade;

CREATE TABLE divisions (
                division_id serial not null primary key,
                division VARCHAR not null,
                commander_position_id int
);

create unique index divisions_division_id_uindex
    on divisions ("division_id");

INSERT INTO divisions (division, commander_position_id) VALUES ('Руководство', 1);
INSERT INTO divisions (division, commander_position_id) VALUES ('Операционный аппарат', 3);
INSERT INTO divisions (division, commander_position_id) VALUES ('Кассовый аппарат', 2);
INSERT INTO divisions (division, commander_position_id) VALUES ('Административный аппарат', 2);
INSERT INTO divisions (division,  commander_position_id) VALUES ('Хозяйственно-эксплуатационный аппарат', 2);
