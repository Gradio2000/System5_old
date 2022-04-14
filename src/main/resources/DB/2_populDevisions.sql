DROP TABLE IF EXISTS division cascade;

CREATE TABLE division (
                division_id serial not null primary key,
                division VARCHAR not null,
                chiff_id int
);

create unique index divisions_division_id_uindex
    on division ("division_id");

INSERT INTO division (division, chiff_id) VALUES ('Руководство', 1);
INSERT INTO division (division, chiff_id) VALUES ('Операционный аппарат', 2);
INSERT INTO division (division, chiff_id) VALUES ('Кассовый аппарат', 3);
INSERT INTO division (division, chiff_id) VALUES ('Административный аппарат', 4);
INSERT INTO division (division, chiff_id) VALUES ('Хозяйственно-эксплуатационный аппарат', 5);
