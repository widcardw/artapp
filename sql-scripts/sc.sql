create table sc
(
    stu_id    int           not null,
    course_id int           not null,
    score     int default 0 null,
    constraint sc_pk
        primary key (stu_id, course_id)
);

