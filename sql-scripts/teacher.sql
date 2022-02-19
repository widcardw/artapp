create table teacher
(
    id           int auto_increment,
    teacher_name char(255)    not null,
    nick_name    varchar(255) null,
    password     char(255)    null,
    constraint teacher_pk
        primary key (id)
)
    comment '教师表';

create unique index teacher_teacher_name_uindex
    on teacher (teacher_name);
