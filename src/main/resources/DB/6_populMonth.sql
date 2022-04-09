DROP TABLE IF EXISTS months CASCADE ;

create table months
(
    month_id   serial
        constraint months_pk
            primary key,
    month_name varchar not null
);

create unique index months_month_id_uindex
    on months (month_id);

insert into months (month_name)
values ('Январь'), ('Февраль'), ('Март'),
       ('Апрель'), ('Май'), ('Июнь'),
       ('Июль'), ('Август'), ('Сентябрь'),
       ('Октябрь'), ('Ноябрь'), ('Декабрь')
