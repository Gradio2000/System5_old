drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence;

DROP TABLE IF EXISTS sys_position_user cascade;
create table sys_position_user
(
    position_id integer,
    user_id     integer
);


DROP TABLE IF EXISTS sys_system5empl cascade;
create table sys_system5empl
(
    resempl1   char,
    resempl2   char,
    resempl3   char,
    resempl4   char,
    resempl5   char,
    system5_id integer not null,
    user_id    integer
);

create unique index system5empl_system5_id_uindex
    on sys_system5empl (system5_id);


DROP TABLE IF EXISTS sys_users cascade;
create table sys_users
(
    user_id  serial
        constraint users_pk
            primary key,
    login    varchar(128),
    name     varchar(128),
    password varchar(256)
);

insert into sys_users (login, name, password) VALUES ('admin', 'admin', '$2a$12$qgc7fFMjfc0or5TdiP.HnegCL0f77FyQ2z3ohZ6.YEkjvmCze/T1a');

create unique index users_user_id_uindex
    on sys_users (user_id);

create index idx_user_name
    on sys_users (name);


DROP TABLE IF EXISTS sys_user_role cascade;
create table sys_user_role
(
    user_id integer not null
        constraint fk
            references sys_users
            on update cascade on delete cascade,
    role    varchar(255),
    constraint user_roles_unique
        unique (user_id, role)
);
insert into sys_user_role (user_id, role) VALUES (1, 'ADMIN');

DROP TABLE IF EXISTS sys_divisions cascade;
create table sys_divisions
(
    division_id           serial
        constraint divisions_pkey
            primary key,
    division              varchar not null,
    commander_position_id integer
);

DROP TABLE IF EXISTS sys_positions cascade;
create table sys_positions
(
    position_id serial
        constraint positions_pkey
            primary key,
    position    varchar not null,
    division_id integer
);

create unique index positions_position_id_uindex
    on sys_positions (position_id);


DROP TABLE IF EXISTS sys_total_mark5 cascade;
create table sys_total_mark5
(
    system5_id_total_mark integer not null,
    total_mark            char,
    total_markempl        char
);


DROP TABLE IF EXISTS sys_system5 cascade;
create table sys_system5
(
    system5_id serial,
    user_id    integer           not null
        constraint system5_users_user_id_fk
            references sys_users
            on update cascade on delete set null,
    month      varchar           not null,
    res1       char              not null,
    res2       char              not null,
    res3       char              not null,
    res4       char              not null,
    res5       char              not null,
    rated      integer default 0 not null
);

create unique index system5_system5_id_uindex
    on sys_system5 (system5_id);

drop table if exists sys_com_empl;
create table sys_com_empl
(
    id      serial
        constraint sys_com_empl_pk
            primary key,
    empl_id integer not null,
    comm_id integer not null
);

create unique index sys_com_empl_id_uindex
    on sys_com_empl (id);