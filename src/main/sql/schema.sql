-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
seckill_id bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
name VARCHAR(120) NOT NULL COMMENT '商品名称',
number bigint NOT NULL COMMENT '库存数量',
create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀条目创建时间',
start_time timestamp NOT NULL COMMENT '秒杀开启时间',
end_time timestamp NOT NULL COMMENT '秒杀结束时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据

insert into
  seckill(name, number, start_time, end_time)
values
  ('1000元秒杀iphone6', 100, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
  ('500元秒杀ipad2', 200, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
  ('300元秒杀小米4', 300, '2015-11-01 00:00:00', '2015-11-02 00:00:00'),
  ('200元秒杀红米note', 400, '2015-11-01 00:00:00', '2015-11-02 00:00:00');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE sucess_killed(
`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL DEFAULT -1 COMMENT '状态标识：-1: 无效 0：秒杀成功 1：已付款',
`create_time` timestamp NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
PRIMARY KEY (seckill_id, user_phone),/*联合主键*/
KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
