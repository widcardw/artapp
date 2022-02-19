create table student
(
    id        int auto_increment,
    username  char(255)    not null,
    password  char(255)    null,
    nick_name varchar(255) null,
    constraint student_pk
        primary key (id)
)
    comment '用户表';

create unique index student_username_uindex
    on student (username);

