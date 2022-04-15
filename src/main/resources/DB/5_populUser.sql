DROP TABLE IF EXISTS users CASCADE ;

create table users
(
    user_id     serial
        constraint users_pk
            primary key,
    login       varchar(128)
        constraint user_email_unique
            unique,
    name        varchar(128),
    password    varchar(256),
    position_id integer
        constraint users_positions_position_id_fk
            references positions
            on update cascade on delete set null
);

alter table users
    owner to aleksejlaskin;

create unique index users_user_id_uindex
    on users (user_id);

create index idx_user_name
    on users (name);


INSERT INTO USERS (login, NAME, PASSWORD, position_id)
VALUES ('aa', 'user', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 2),
       ('bb', 'admin', '$2a$12$WIr/8OS3JN02CakLS9RiyuQuWm8Fk7XybV7WyVzidNfKoZJ.hxFSi', 1)
