drop table if exists user;

create table user
(
  id int auto_increment primary key,
  name varchar(256) not null comment '氏名',
  mail_address varchar(256) not null comment 'メールアドレス',
  password varchar(256) not null comment 'パスワード',
  deleted int(1) default 0 comment '削除フラグ',
  created timestamp default CURRENT_TIMESTAMP not null,
  updated timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);