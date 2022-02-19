create table admin
(
    id         int auto_increment,
    admin_name char(255)    not null,
    nick_name  varchar(255) null,
    password   char(255)    null,
    constraint admin_pk
        primary key (id)
)
    comment '管理员表';

create unique index admin_admin_name_uindex
    on admin (admin_name);
