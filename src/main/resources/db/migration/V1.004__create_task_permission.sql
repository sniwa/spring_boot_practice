drop table if exists  introduction.task_permissions;

create table introduction.task_permissions
(
  task_id int not null comment 'タスクID',
  user_id int not null comment 'ユーザID',
  constraint task_permissions_task_id_fk
    foreign key (task_id) references introduction.task (id)
      on delete cascade,
  constraint task_permissions_user_id_fk
    foreign key (user_id) references introduction.user (id)
      on delete cascade
)
  comment 'タスク権限';