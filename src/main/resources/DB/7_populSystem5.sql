DROP TABLE IF EXISTS system5 CASCADE ;

create table system5
(
    system5_id serial,
    user_id    integer           not null
        constraint system5_users_user_id_fk
            references users
            on update cascade on delete set null,
    month      varchar           not null,
    res1       char              not null,
    res2       char              not null,
    res3       char              not null,
    res4       char              not null,
    res5       char              not null,
    rated      integer default 0 not null
);

alter table system5
    owner to aleksejlaskin;

create unique index system5_system5_id_uindex
    on system5 (system5_id);

