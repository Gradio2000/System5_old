DROP TABLE IF EXISTS system5 CASCADE ;

create table system5
(
    system5_id serial
        constraint system5_pk
            primary key,
    user_id    integer           not null,
    month      varchar           not null,
    res1       char              not null,
    res2       char              not null,
    res3       char              not null,
    res4       char              not null,
    res5       char              not null,
    rated      integer default 0 not null,
    resempl1   char,
    resempl2   char,
    resempl3   char,
    resempl4   char,
    resempl5   char
);

create unique index system5_system5_id_uindex
    on system5 (system5_id);


