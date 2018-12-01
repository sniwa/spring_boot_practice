drop table if exists task;

create table task
(
  id int auto_increment primary key,
  title text null comment 'タイトル',
  text text null comment '本文',
  deleted int(1) default 0 comment '削除フラグ',
  created timestamp default CURRENT_TIMESTAMP not null,
  updated timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);