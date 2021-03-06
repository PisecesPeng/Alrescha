-- 创建database
CREATE DATABASE alrescha;
-- 使用database
use alrescha;

-- 用户
CREATE TABLE `al_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) COMMENT '密码',
  `salt` varchar(20) COMMENT '盐',
  `email` varchar(100) COMMENT '邮箱',
  `status` tinyint COMMENT '状态  0：禁用  1：正常',
  `create_time` datetime COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- 用户Token
CREATE TABLE `user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(50) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

-- 初始数据(id:'admin',pw:'admin')
INSERT INTO `al_user` (`user_id`, `username`, `password`, `salt`, `email`, `status`, `create_time`) VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'Piseces.Peng@gmail.com', '1', '2018-10-01 00:00:00');