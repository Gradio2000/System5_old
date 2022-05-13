DROP TABLE IF EXISTS users CASCADE ;

create table users
(
    user_id  serial
        constraint users_pk
            primary key,
    login    varchar(128),
    name     varchar(128),
    password varchar(256)
);

alter table users
    owner to aleksejlaskin;

create unique index users_user_id_uindex
    on users (user_id);

create index idx_user_name
    on users (name);


INSERT INTO USERS (login, NAME, PASSWORD)
VALUES ('aa', 'Ласкин', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi'),
       ('bb', 'Даянов', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi'),
       ('cc', 'Степанов', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi'),
       ('dd', 'Першина', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi'),
       ('ee', 'Лапшина', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi'),
       ('ff', 'Сытник', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi'),
       ('gg', 'Маликов', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi'),
       ('hh', 'Кузнецов', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi'),
       ('zz', 'admin', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi');

DROP TABLE IF EXISTS USER_ROLE CASCADE;

create table user_role
(
    user_id integer not null
        constraint fk
            references users
            on update cascade on delete cascade,
    role    varchar(255),
    constraint user_roles_unique
        unique (user_id, role)
);

alter table user_role
    owner to aleksejlaskin;

insert into user_role (USER_ID, ROLE)
VALUES (1, 'USER'), (2, 'USER'), (3, 'USER'),  (4, 'USER'),
       (5, 'USER'), (6, 'USER'), (7, 'USER'), (8, 'ADMIN');