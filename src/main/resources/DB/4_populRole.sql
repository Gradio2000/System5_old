DROP TABLE IF EXISTS USER_ROLE CASCADE;

create table USER_ROLE
(
    USER_ID INTEGER not null,
    ROLE    VARCHAR(255),
    constraint USER_ROLES_UNIQUE
        unique (USER_ID, ROLE),
    constraint FKJ345GK1BOVQVFAME88RCX7YYX
        foreign key (USER_ID) references USERS (USER_ID)
);

insert into user_role (USER_ID, ROLE)
VALUES (1, 'ADMIN'), (2, 'USER')