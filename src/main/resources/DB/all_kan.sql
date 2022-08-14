drop table if exists kan_kanban;

create table kan_kanban
(
    kanban_id   serial not null
        constraint kanban_id
            primary key,
    kanban_name varchar,
    started     boolean,
    continues   boolean,
    finished    boolean
);

