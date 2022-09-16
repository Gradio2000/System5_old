drop table if exists kan_kanban;

create table kan_kanban
(
    kanban_id      int IDENTITY(1, 1)
        constraint kanban_id
            primary key,
    kanban_name    varchar,
    started        bool,
    continues      boolean,
    finished       boolean,
    describe       varchar,
    author_user_id integer,
    taskenddate     date
);

drop table if exists kan_kanban_users;
create table kan_kanban_users
(
    id        serial
        constraint kan_kanban_users_pk
            primary key,
    kanban_id integer not null,
    user_id   integer not null
);



