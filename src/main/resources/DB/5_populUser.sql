DROP TABLE IF EXISTS users CASCADE ;

create table USERS
(
    USER_ID
        serial constraint users_pk primary key,
    EMAIL    VARCHAR(128)
        constraint USER_EMAIL_UNIQUE
            unique,
    NAME     VARCHAR(128),
    PASSWORD VARCHAR(256)
);

create unique index users_user_id_uindex
    on USERS (USER_ID);

create index IDX_USER_NAME
    on USERS (NAME);

INSERT INTO USERS (EMAIL, NAME, PASSWORD)
VALUES ('aa@aa.aa', 'user', '1'), ('bb@bb.bb', 'admin', '1')
