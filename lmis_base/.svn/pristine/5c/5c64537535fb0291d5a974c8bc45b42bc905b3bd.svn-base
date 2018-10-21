--描述：系统异常日志记录表
--开发人：徐一粟
--时间：2018年05月10日

DROP TABLE IF EXISTS `sys_err_log`;
CREATE TABLE `sys_err_log` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建对象',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改对象',
  `is_deleted` tinyint(1) DEFAULT NULL COMMENT '逻辑删除标志 0-未删除 1-已删除',
  `is_disabled` tinyint(1) DEFAULT NULL COMMENT '启停标志 1-已禁用 0-未禁用',
  `version` int(10) DEFAULT NULL COMMENT '版本号',
  `pwr_org` varchar(50) DEFAULT NULL COMMENT '权限架构',
  `by_function` varchar(100) DEFAULT NULL COMMENT '调用方法',
  `err_data` text COMMENT '异常数据',
  `err_log` text COMMENT '异常信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;