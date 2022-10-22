drop table if exists kladr_all;
create table kladr_all
(
    id         serial
        constraint kladr_all_pk
            primary key,
    reg_code   integer,
    area_code  integer,
    name       varchar,
    city_code  integer,
    punkt_code integer
);

create index kladr_all_name_index
    on kladr_all (name, id);


create table kl_street
(
    id          serial
        constraint kl_street_pk
            primary key,
    reg_code    integer,
    area_code   integer,
    city_code   integer,
    punkt_code  integer,
    street_code integer,
    name        varchar,
    socr        varchar,
    index       varchar,
    constraint kl_street_pk_2
        unique (reg_code, area_code, city_code, punkt_code, id)
);

create unique index kl_street_id_uindex
    on kl_street (id);

create table kl_house
(
    id          serial
        constraint kl_house_pk
            primary key,
    reg_code    integer,
    area_code   integer,
    city_code   integer,
    punkt_code  integer,
    street_code integer,
    house_code  integer,
    name        varchar,
    index       varchar,
    constraint kl_house_pk_2
        unique (reg_code, area_code, city_code, punkt_code, street_code, id)
);

create unique index kl_house_id
    on kl_house (reg_code, area_code, city_code, punkt_code, street_code, id);

