alter table task
  add column completed int(1) default 0 comment '完了フラグ' not null after text,
  add column priority int default 0 comment '優先度' not null after completed,
  add column  completed_date timestamp default null after completed
;
