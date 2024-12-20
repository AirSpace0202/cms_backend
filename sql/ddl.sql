-- 创建库
create database if not exists manage_system;

-- 切换库
use manage_system;

drop table user;
-- 系统用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userName     varchar(256)                           null comment '用户昵称',
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user / admin',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    constraint uni_userAccount
        unique (userAccount)
) comment '系统用户';

-- 客户表
create table if not exists customer
(
    id            bigint auto_increment comment 'id' primary key,
    custName        varchar(256)    not null comment '客户名称',
    custUserId      int             not null comment '负责人id',
    custSource      varchar(512)        not null comment '客户信息来源',
    custIndustry    varchar(512)        not null comment '客户行业',
    gender        tinyint  default 0                 not null comment '性别（0-男, 1-女）',
    phone       varchar(512)        not null comment '客户电话',
    level       int         default 0   comment '客户级别',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '客户表';

-- 订单表
create table if not exists `orderInfo`
(
    `id` bigint  auto_increment comment '订单编号' primary key,
    `custId` bigint not null comment '客户编号',
    `product` varchar(512) not null comment '商品名',
    `totalNum` decimal(10, 2) not null comment '订单金额',
    `status` tinyint default 0 not null comment '订单状态 0-未完成 1-已完成',
    `method` tinyint default 0 not null comment '支付方式 0-支付宝 1-微信',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '订单';

-- 消息表
create table if not exists `msg`
(
    `id` bigint not null auto_increment comment '消息编号' primary key,
    `custId` bigint not null comment '客户编号',
    `msgContent` text not null comment '消息内容',
    createTime    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint  default 0                 not null comment '是否删除'
) comment '消息表';