DROP TABLE IF EXISTS divisions cascade;

create table divisions
(
    division_id           serial
        constraint divisions_pkey
            primary key,
    division              varchar not null,
    commander_position_id integer
);

alter table divisions
    owner to aleksejlaskin;

create unique index divisions_division_id_uindex
    on divisions (division_id);


INSERT INTO divisions (division, commander_position_id) VALUES ('Руководство', 1);
INSERT INTO divisions (division, commander_position_id) VALUES ('Операционный аппарат', 3);
INSERT INTO divisions (division, commander_position_id) VALUES ('Кассовый аппарат', 2);
INSERT INTO divisions (division, commander_position_id) VALUES ('Административный аппарат', 2);
INSERT INTO divisions (division,  commander_position_id) VALUES ('Хозяйственно-эксплуатационный аппарат', 2);
