DROP TABLE IF EXISTS system5 CASCADE ;

create table system5
(
    system5_id serial
        constraint system5_pk
            primary key,
    user_id    int not null,
    month      varchar not null,
    res1       char(1) not null,
    res2       char(1) not null,
    res3       char(1) not null,
    res4       char(1) not null,
    res5       char(1) not null
);

create unique index system5_system5_id_uindex
    on system5 (system5_id);



insert into system5 (user_id, month, res1, res2, res3, res4, res5)
values ('1', 'Январь', 'A', 'B', 'C', 'D', 'E')
