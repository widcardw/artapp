create table course
(
    id          int auto_increment,
    course_no   char(255)    not null,
    course_name varchar(255) null,
    constraint course_pk
        primary key (id)
)
    comment '课程表';

create unique index course_course_no_uindex
    on course (course_no);

