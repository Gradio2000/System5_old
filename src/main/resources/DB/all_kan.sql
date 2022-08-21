drop table if exists kan_kanban;

create table kan_kanban
(
    kanban_id      serial
        constraint kanban_id
            primary key,
    kanban_name    varchar,
    started        boolean,
    continues      boolean,
    finished       boolean,
    describe       varchar,
    author_user_id integer,
    taskenddate     date
);



