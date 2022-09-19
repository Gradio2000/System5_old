DROP TABLE IF EXISTS sys_position_user;
create table sys_position_user
(
    position_id integer,
    user_id     integer
);


DROP TABLE IF EXISTS sys_system5empl;
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


DROP TABLE IF EXISTS sys_users;
create table sys_users
(
    user_id  int IDENTITY (1, 1) primary key,
    login    nvarchar(128),
    name     nvarchar(128),
    password nvarchar(256)
);

insert into sys_users (login, name, password) VALUES ('admin', 'admin', '$2a$12$qgc7fFMjfc0or5TdiP.HnegCL0f77FyQ2z3ohZ6.YEkjvmCze/T1a');


DROP TABLE IF EXISTS sys_user_role;
create table sys_user_role
(
    user_id int IDENTITY (1, 1) primary key,
    role    nvarchar(255),
);
insert into sys_user_role (role) VALUES ('ADMIN');

DROP TABLE IF EXISTS sys_divisions;
create table sys_divisions
(
    division_id           int IDENTITY (1, 1) primary key,
    division              nvarchar(255),
    commander_position_id integer
);

DROP TABLE IF EXISTS sys_positions;
create table sys_positions
(
    position_id int IDENTITY (1, 1) primary key,
    position    nvarchar(255),
    division_id integer
);

DROP TABLE IF EXISTS sys_total_mark5;
create table sys_total_mark5
(
    system5_id_total_mark int IDENTITY (1, 1) primary key,
    total_mark            char,
    total_markempl        char
);


DROP TABLE IF EXISTS sys_system5;
create table sys_system5
(
    system5_id int IDENTITY (1, 1) primary key,
    user_id    integer           ,
    month      nvarchar(255)           not null,
    res1       char              not null,
    res2       char              not null,
    res3       char              not null,
    res4       char              not null,
    res5       char              not null,
    rated      integer default 0 not null
);

drop table if exists sys_com_empl;
create table sys_com_empl
(
    id      int IDENTITY (1, 1) primary key,
    empl_id integer not null,
    comm_id integer not null
);
