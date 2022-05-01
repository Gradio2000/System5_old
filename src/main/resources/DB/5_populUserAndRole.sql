DROP TABLE IF EXISTS users CASCADE ;

create table users
(
    user_id     serial
        constraint users_pk
            primary key,
    login       varchar(128),
    name        varchar(128),
    password    varchar(256),
    position_id integer
--         constraint users_positions_position_id_fk
--             references positions
--             on update cascade on delete set null
);

alter table users
    owner to aleksejlaskin;

create unique index users_user_id_uindex
    on users (user_id);

create index idx_user_name
    on users (name);


INSERT INTO USERS (login, NAME, PASSWORD, position_id)
VALUES ('aa', 'Ласкин', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 2),
       ('bb', 'Даянов', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 3),
       ('cc', 'Степанов', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 4),
--        ('dd', 'Першина', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 4),
       ('ee', 'Лапшина', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 6),
       ('ff', 'Сытник', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 7),
       ('gg', 'Маликов', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 8),
       ('hh', 'Кузнецов', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 9),
       ('zz', 'admin', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 1);

DROP TABLE IF EXISTS USER_ROLE CASCADE;

create table USER_ROLE
(
    USER_ID INTEGER not null,
    ROLE    VARCHAR(255),
    constraint USER_ROLES_UNIQUE
        unique (USER_ID, ROLE),
    constraint FKJ345GK1BOVQVFAME88RCX7YYX
        foreign key (USER_ID) references USERS (USER_ID)
);

insert into user_role (USER_ID, ROLE)
VALUES (1, 'USER'), (2, 'USER'), (3, 'USER'),  (4, 'USER'),
       (5, 'USER'), (6, 'USER'), (7, 'USER'), (8, 'ADMIN');