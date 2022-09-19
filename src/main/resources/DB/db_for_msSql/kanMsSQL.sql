create table kan_kanban
(
    kanban_id      int IDENTITY(1,1) primary key,
    kanban_name    nvarchar(255),
    started        bit,
    continues      bit,
    finished       bit,
    describe       nvarchar(255),
    author_user_id integer,
    taskenddate     date
);


create table kan_kanban_users
(
    id        int IDENTITY(1,1) primary key,
    kanban_id integer not null,
    user_id   integer not null
);



