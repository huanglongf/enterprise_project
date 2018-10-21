-- 2.4 setup_page_element
ALTER TABLE `setup_page_element` 
ADD COLUMN `column_len` INT(10) DEFAULT NULL COMMENT '字段长度',
ADD COLUMN `element_hide` TINYINT(1) DEFAULT NULL COMMENT '元素是否隐藏',
ADD COLUMN `is_filt` TINYINT(1) DEFAULT NULL COMMENT '字符过滤';



-- 2.4  setup_page_element 视图添加列 column_len,element_hide,is_filt
CREATE
OR REPLACE VIEW view_setup_page_element AS SELECT
	`setup_page_element`.`id` AS `id`,
	`setup_page_element`.`create_time` AS `create_time`,
	`setup_page_element`.`create_by` AS `create_by`,
	`setup_page_element`.`update_time` AS `update_time`,
	`setup_page_element`.`update_by` AS `update_by`,
	`setup_page_element`.`is_deleted` AS `is_deleted`,
	`setup_page_element`.`is_disabled` AS `is_disabled`,
	`setup_page_element`.`version` AS `version`,
	`setup_page_element`.`pwr_org` AS `pwr_org`,
	`setup_page_element`.`element_id` AS `element_id`,
	`setup_page_element`.`element_name` AS `element_name`,
	`setup_page_element`.`layout_id` AS `layout_id`,
	`setup_page_element`.`element_seq` AS `element_seq`,
	`setup_page_element`.`element_width` AS `element_width`,
	`setup_page_element`.`element_height` AS `element_height`,
	`setup_page_element`.`element_type` AS `element_type`,
	`setup_page_element`.`default_value` AS `default_value`,
	`setup_page_element`.`element_disabled` AS `element_disabled`,
	`setup_page_element`.`element_format` AS `element_format`,
	`setup_page_element`.`table_id` AS `table_id`,
	`setup_page_element`.`column_id` AS `column_id`,
	`setup_page_element`.`column_type` AS `column_type`,
	`setup_page_element`.`where_type` AS `where_type`,
	`setup_page_element`.`where_operator` AS `where_operator`,
	`setup_page_element`.`add_readonly` AS `add_readonly`,
	`setup_page_element`.`update_readonly` AS `update_readonly`,
	`setup_page_element`.`button_click` AS `button_click`,
	`setup_page_element`.`not_null` AS `not_null`,
	`setup_page_element`.`add_not_null` AS `add_not_null`,
	`setup_page_element`.`update_not_null` AS `update_not_null`,
	`setup_page_element`.`button_set` AS `button_set`,
	`setup_page_element`.`button_picture` AS `button_picture`,
	`setup_page_element`.`element_title` AS `element_title`,
	`setup_page_element`.`column_len` AS `column_len`,
	`setup_page_element`.`element_hide` AS `element_hide`,
	`setup_page_element`.`is_filt` AS `is_filt`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'constant_y_n'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`is_filt`
		)
	) AS `is_filt_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'constant_y_n'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`element_hide`
		)
	) AS `element_hide_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'constant_y_n'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`add_readonly`
		)
	) AS `add_readonly_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'constant_y_n'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`update_readonly`
		)
	) AS `update_readonly_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'constant_y_n'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`element_disabled`
		)
	) AS `element_disabled_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'constant_y_n'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`not_null`
		)
	) AS `not_null_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'constant_y_n'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`add_not_null`
		)
	) AS `add_not_null_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'constant_y_n'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`update_not_null`
		)
	) AS `update_not_null_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'html_element'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`element_type`
		)
	) AS `element_type_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'button_click'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`button_click`
		)
	) AS `button_click_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				`a`.`group_code` = 'button_set'
			)
		AND (`a`.`is_deleted` = 0)
		AND (
			`a`.`constant_code` = `setup_page_element`.`button_set`
		)
	) AS `button_set_display`
FROM
	`setup_page_element`
WHERE
	`setup_page_element`.`is_deleted` = 0;
	
-- 2.5 setup_page_table
ALTER TABLE `setup_page_table`
ADD COLUMN `list_type` VARCHAR(10) DEFAULT NULL COMMENT '列表类型(普通列表/列表按钮)',
ADD COLUMN `column_hide` TINYINT(1) DEFAULT NULL COMMENT '是否隐藏 setup_constant_sql.sql_code="constant_y_n"';

-- 2.5 视图添加修改 setup_page_table 表的列 column_hide、list_type
CREATE
OR REPLACE VIEW view_setup_page_table AS SELECT
	`setup_page_table`.`id` AS `id`,
	`setup_page_table`.`create_time` AS `create_time`,
	`setup_page_table`.`create_by` AS `create_by`,
	`setup_page_table`.`update_time` AS `update_time`,
	`setup_page_table`.`update_by` AS `update_by`,
	`setup_page_table`.`is_deleted` AS `is_deleted`,
	`setup_page_table`.`is_disabled` AS `is_disabled`,
	`setup_page_table`.`version` AS `version`,
	`setup_page_table`.`pwr_org` AS `pwr_org`,
	`setup_page_table`.`column_id` AS `column_id`,
	`setup_page_table`.`column_name` AS `column_name`,
	`setup_page_table`.`layout_id` AS `layout_id`,
	`setup_page_table`.`column_seq` AS `column_seq`,
	`setup_page_table`.`column_width` AS `column_width`,
	`setup_page_table`.`column_height` AS `column_height`,
	`setup_page_table`.`column_format` AS `column_format`,
	`setup_page_table`.`table_id` AS `table_id`,
	`setup_page_table`.`table_column_id` AS `table_column_id`,
	`setup_page_table`.`column_type` AS `column_type`,
	`setup_page_table`.`orderby_type` AS `orderby_type`,
	`setup_page_table`.`orderby_seq` AS `orderby_seq`,
	`setup_page_table`.`count_type` AS `count_type`,
	`setup_page_table`.`count_name` AS `count_name`,
	`setup_page_table`.`column_hide` AS `column_hide`,
	`setup_page_table`.`list_type` AS `list_type`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				(
					`a`.`group_code` = 'constant_y_n'
				)
				AND (`a`.`is_deleted` = 0)
				AND (
					`a`.`constant_code` = `setup_page_table`.`column_hide`
				)
			)
	) AS `column_hide_display`,
	(
		SELECT
			`a`.`constant_name` AS `name`
		FROM
			`setup_constant` `a`
		WHERE
			(
				(
					`a`.`group_code` = 'list_type'
				)
				AND (`a`.`is_deleted` = 0)
				AND (
					`a`.`constant_code` = `setup_page_table`.`list_type`
				)
			)
	) AS `list_type_display`
FROM
	`setup_page_table`;

-- 修改布局视图
CREATE
OR REPLACE VIEW view_setup_page_layout AS SELECT `setup_page_layout`.`id` AS `id`,`setup_page_layout`.`create_time` AS `create_time`,`setup_page_layout`.`create_by` AS `create_by`,`setup_page_layout`.`update_time` AS `update_time`,`setup_page_layout`.`update_by` AS `update_by`,`setup_page_layout`.`is_deleted` AS `is_deleted`,`setup_page_layout`.`is_disabled` AS `is_disabled`,`setup_page_layout`.`version` AS `version`,`setup_page_layout`.`pwr_org` AS `pwr_org`,`setup_page_layout`.`layout_id` AS `layout_id`,`setup_page_layout`.`page_id` AS `page_id`,`setup_page_layout`.`layout_name` AS `layout_name`,`setup_page_layout`.`layout_seq` AS `layout_seq`,`setup_page_layout`.`layout_width` AS `layout_width`,`setup_page_layout`.`layout_height` AS `layout_height`,`setup_page_layout`.`layout_type` AS `layout_type`,`setup_page_layout`.`parent_layout_id` AS `parent_layout_id`,`setup_page_layout`.`button_flag1` AS `button_flag1`,`setup_page_layout`.`button_flag2` AS `button_flag2`,`setup_page_layout`.`button_flag3` AS `button_flag3`,`setup_page_layout`.`button_flag4` AS `button_flag4`,`setup_page_layout`.`button_flag5` AS `button_flag5`, (select a.constant_name as name from setup_constant a where a.group_code='constant_y_n' and a.is_deleted=0 and a.constant_code = `button_flag1`) AS `btn_config`, (select a.constant_name as name from setup_constant a where a.group_code='constant_y_n' and a.is_deleted=0 and a.constant_code = `button_flag2`) AS `btn_export` , (select a.constant_name as name from setup_constant a where a.group_code='constant_y_n' and a.is_deleted=0 and a.constant_code = `button_flag3`) AS `btn_import` , (select a.constant_name as name from setup_constant a where a.group_code='constant_y_n' and a.is_deleted=0 and a.constant_code = `button_flag4`) AS `btn_fold` , (select a.constant_name as name from setup_constant a where a.group_code='constant_y_n' and a.is_deleted=0 and a.constant_code = `button_flag5`) AS `btn_hide`  from `setup_page_layout`;

-- 新增布局视图，用于查询减少多余字段过滤需要
CREATE
OR REPLACE VIEW view_setup_page_layout_for_simple AS SELECT
	`setup_page_layout`.`id` AS `id`,
	`setup_page_layout`.`create_time` AS `create_time`,
	`setup_page_layout`.`create_by` AS `create_by`,
	`setup_page_layout`.`update_time` AS `update_time`,
	`setup_page_layout`.`update_by` AS `update_by`,
	`setup_page_layout`.`is_deleted` AS `is_deleted`,
	`setup_page_layout`.`is_disabled` AS `is_disabled`,
	`setup_page_layout`.`version` AS `version`,
	`setup_page_layout`.`pwr_org` AS `pwr_org`,
	`setup_page_layout`.`layout_id` AS `layout_id`,
	`setup_page_layout`.`page_id` AS `page_id`,
	`setup_page_layout`.`layout_name` AS `layout_name`,
	`setup_page_layout`.`layout_seq` AS `layout_seq`,
	`setup_page_layout`.`layout_width` AS `layout_width`,
	`setup_page_layout`.`layout_height` AS `layout_height`,
	`setup_page_layout`.`layout_type` AS `layout_type`,
	`setup_page_layout`.`parent_layout_id` AS `parent_layout_id`,
	`setup_page_layout`.`button_flag1` AS `button_flag1`,
	`setup_page_layout`.`button_flag2` AS `button_flag2`,
	`setup_page_layout`.`button_flag3` AS `button_flag3`,
	`setup_page_layout`.`button_flag4` AS `button_flag4`,
	`setup_page_layout`.`button_flag5` AS `button_flag5`
FROM
	`setup_page_layout`;

-- 添加新的元素视图，用于简单查询
CREATE
OR REPLACE VIEW view_setup_page_element_for_simple AS SELECT
	`setup_page_element`.`id` AS `id`,
	`setup_page_element`.`create_time` AS `create_time`,
	`setup_page_element`.`create_by` AS `create_by`,
	`setup_page_element`.`update_time` AS `update_time`,
	`setup_page_element`.`update_by` AS `update_by`,
	`setup_page_element`.`is_deleted` AS `is_deleted`,
	`setup_page_element`.`is_disabled` AS `is_disabled`,
	`setup_page_element`.`version` AS `version`,
	`setup_page_element`.`pwr_org` AS `pwr_org`,
	`setup_page_element`.`element_id` AS `element_id`,
	`setup_page_element`.`element_name` AS `element_name`,
	`setup_page_element`.`layout_id` AS `layout_id`,
	`setup_page_element`.`element_seq` AS `element_seq`,
	`setup_page_element`.`element_width` AS `element_width`,
	`setup_page_element`.`element_height` AS `element_height`,
	`setup_page_element`.`element_type` AS `element_type`,
	`setup_page_element`.`default_value` AS `default_value`,
	`setup_page_element`.`element_disabled` AS `element_disabled`,
	`setup_page_element`.`element_format` AS `element_format`,
	`setup_page_element`.`table_id` AS `table_id`,
	`setup_page_element`.`column_id` AS `column_id`,
	`setup_page_element`.`column_type` AS `column_type`,
	`setup_page_element`.`where_type` AS `where_type`,
	`setup_page_element`.`where_operator` AS `where_operator`,
	`setup_page_element`.`add_readonly` AS `add_readonly`,
	`setup_page_element`.`update_readonly` AS `update_readonly`,
	`setup_page_element`.`button_click` AS `button_click`,
	`setup_page_element`.`not_null` AS `not_null`,
	`setup_page_element`.`add_not_null` AS `add_not_null`,
	`setup_page_element`.`update_not_null` AS `update_not_null`,
	`setup_page_element`.`button_set` AS `button_set`,
	`setup_page_element`.`button_picture` AS `button_picture`,
	`setup_page_element`.`element_title` AS `element_title`,
	`setup_page_element`.`column_len` AS `column_len`,
	`setup_page_element`.`element_hide` AS `element_hide`,
	`setup_page_element`.`is_filt` AS `is_filt`
FROM
	`setup_page_element`
WHERE
	(
		`setup_page_element`.`is_deleted` = 0
	);

-- 添加新的查询列表视图，用于简单查询
CREATE
OR REPLACE VIEW view_setup_page_table_for_simple AS SELECT
	`setup_page_table`.`id` AS `id`,
	`setup_page_table`.`create_time` AS `create_time`,
	`setup_page_table`.`create_by` AS `create_by`,
	`setup_page_table`.`update_time` AS `update_time`,
	`setup_page_table`.`update_by` AS `update_by`,
	`setup_page_table`.`is_deleted` AS `is_deleted`,
	`setup_page_table`.`is_disabled` AS `is_disabled`,
	`setup_page_table`.`version` AS `version`,
	`setup_page_table`.`pwr_org` AS `pwr_org`,
	`setup_page_table`.`column_id` AS `column_id`,
	`setup_page_table`.`column_name` AS `column_name`,
	`setup_page_table`.`layout_id` AS `layout_id`,
	`setup_page_table`.`column_seq` AS `column_seq`,
	`setup_page_table`.`column_width` AS `column_width`,
	`setup_page_table`.`column_height` AS `column_height`,
	`setup_page_table`.`column_format` AS `column_format`,
	`setup_page_table`.`table_id` AS `table_id`,
	`setup_page_table`.`table_column_id` AS `table_column_id`,
	`setup_page_table`.`column_type` AS `column_type`,
	`setup_page_table`.`orderby_type` AS `orderby_type`,
	`setup_page_table`.`orderby_seq` AS `orderby_seq`,
	`setup_page_table`.`count_type` AS `count_type`,
	`setup_page_table`.`count_name` AS `count_name`,
	`setup_page_table`.`column_hide` AS `column_hide`,
	`setup_page_table`.`list_type` AS `list_type`
FROM
	`setup_page_table`;

	
-- SQL 脚本同步：

-- 页面布局编号配置同步
DELETE
FROM
	sys_coderule_info
WHERE
	config_code = 'PAGE_NUM'
AND is_deleted = FALSE ;

INSERT INTO `sys_coderule_info` (
	`id`,
	`create_time`,
	`create_by`,
	`update_time`,
	`update_by`,
	`is_deleted`,
	`is_disabled`,
	`version`,
	`pwr_org`,
	`remark`,
	`config_name`,
	`config_code`
)
VALUES
	(
		'115e272e-6476-11e8-9d0d-005056954f8e',
		'2018-05-30 14:30:43',
		'kaihua.dai',
		NULL,
		'kaihua.dai',
		'0',
		'0',
		'1',
		NULL,
		NULL,
		'页面布局编号',
		'PAGE_NUM'
	);

-- sys_coderule_data 添加数据

DELETE
FROM
	sys_coderule_data
WHERE
	config_code = 'PAGE_NUM'
AND is_deleted = FALSE;

INSERT INTO `sys_coderule_data` (
	`id`,
	`create_time`,
	`create_by`,
	`update_time`,
	`update_by`,
	`is_deleted`,
	`is_disabled`,
	`version`,
	`pwr_org`,
	`remark`,
	`rule_code`,
	`config_code`,
	`data_order`,
	`start_value`,
	`incre_value`,
	`number`,
	`data_valuelg`,
	`update_cycle`
)
VALUES
	(
		'2f79467b-6476-11e8-9d0d-005056954f8e',
		'2018-05-31 09:59:00',
		'kaihua.dai',
		'2018-05-31 09:59:00',
		'kaihua.dai',
		'0',
		'0',
		'1',
		NULL,
		NULL,
		'20180524351678',
		'PAGE_NUM',
		'1',
		'PN1',
		NULL,
		NULL,
		'3',
		NULL
	);

INSERT INTO `sys_coderule_data` (
	`id`,
	`create_time`,
	`create_by`,
	`update_time`,
	`update_by`,
	`is_deleted`,
	`is_disabled`,
	`version`,
	`pwr_org`,
	`remark`,
	`rule_code`,
	`config_code`,
	`data_order`,
	`start_value`,
	`incre_value`,
	`number`,
	`data_valuelg`,
	`update_cycle`
)
VALUES
	(
		'3ef660a1-6476-11e8-9d0d-005056954f8e',
		'2018-05-31 09:59:26',
		'kaihua.dai',
		'2018-05-31 09:59:26',
		'kaihua.dai',
		'0',
		'0',
		'1',
		NULL,
		NULL,
		'20180524358469',
		'PAGE_NUM',
		'2',
		'1',
		'1',
		'5',
		'1',
		'cycle_month'
	);
	
	
-- setup_constant 添加普通列表/列表按钮
delete from setup_constant  where constant_code = 'lt_plain';
delete from setup_constant  where constant_code = 'lt_btn';
INSERT INTO `setup_constant` (
	`id`,
	`create_time`,
	`create_by`,
	`update_time`,
	`update_by`,
	`is_deleted`,
	`is_disabled`,
	`version`,
	`pwr_org`,
	`constant_code`,
	`constant_name`,
	`group_code`,
	`group_name`,
	`constant_seq`,
	`constant_name1`,
	`constant_name2`,
	`remark`
)
VALUES
	(
		'e0178d57-64af-11e8-9d0d-005056954f8e',
		'2018-05-31 16:51:57',
		'1',
		'2018-05-31 16:51:57',
		'1',
		'0',
		'0',
		'1',
		'logistics_center',
		'lt_plain',
		'普通列表',
		'list_type',
		'列表类型',
		'10',
		NULL,
		NULL,
		NULL
	);

INSERT INTO `setup_constant` (
	`id`,
	`create_time`,
	`create_by`,
	`update_time`,
	`update_by`,
	`is_deleted`,
	`is_disabled`,
	`version`,
	`pwr_org`,
	`constant_code`,
	`constant_name`,
	`group_code`,
	`group_name`,
	`constant_seq`,
	`constant_name1`,
	`constant_name2`,
	`remark`
)
VALUES
	(
		'f6fd0220-64af-11e8-9d0d-005056954f8e',
		'2018-05-31 16:52:36',
		'1',
		'2018-05-31 16:52:36',
		'1',
		'0',
		'0',
		'1',
		'logistics_center',
		'lt_btn',
		'列表按钮',
		'list_type',
		'列表类型',
		'20',
		NULL,
		NULL,
		NULL
	);

-- 列表类型 常量 SQL 添加
DELETE
FROM
	setup_constant_sql
WHERE
	sql_code = 'list_type';

INSERT INTO `setup_constant_sql` (
	`id`,
	`create_time`,
	`create_by`,
	`update_time`,
	`update_by`,
	`is_deleted`,
	`is_disabled`,
	`version`,
	`pwr_org`,
	`sql_code`,
	`sql_name`,
	`sql_remark`,
	`default_value`,
	`is_empty`
)
VALUES
	(
		'82188dbf-6549-11e8-9d0d-005056954f8e',
		'2018-06-01 11:11:42',
		'1',
		'2018-06-01 11:11:42',
		'1',
		'0',
		'0',
		'1',
		'logistics_center',
		'list_type',
		'列表类型',
		'select a.constant_code as code,a.constant_name as name from setup_constant a where a.group_code=\"list_type\" and a.is_deleted=0 order by a.constant_seq',
		NULL,
		NULL
	);
	
-- 新增页面布局2
DELETE
FROM
	setup_page
WHERE
	page_id = 'P_YMBJ2';
	
INSERT INTO `setup_page` (
		`id`,
		`create_time`,
		`create_by`,
		`update_time`,
		`update_by`,
		`is_deleted`,
		`is_disabled`,
		`version`,
		`pwr_org`,
		`page_id`,
		`page_name`,
		`page_width`,
		`page_height`,
		`page_type`,
		`parent_page_id`,
		`page_url`
	)
VALUES
	(
		'73f7edde-63d9-11e8-9d0d-005056954f8e',
		'2018-05-30 15:17:03',
		'admin',
		'2018-05-30 15:17:03',
		'admin',
		'0',
		'0',
		'1',
		'lmis_bcs',
		'P_YMBJ2',
		'页面布局2',
		NULL,
		NULL,
		'page_default',
		NULL,
		NULL
	);

-- 页面布局2 SQL 脚本同步
delete from setup_page_layout where page_id = 'P_YMBJ2';

INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('e2c8763d-5ca6-11e8-bd1b-005056952d2b', '2018-05-21 11:27:27', '1', '2018-06-06 09:47:19', 'kaihua.dai', '0', '0', '1', 'logistics_center', 'P_YMBJ2_P01', 'P_YMBJ2', '页面配置', '10', '100%', NULL, 'layout_a', NULL, '0', '0', '0', '1', NULL, NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('f1093a13-5ca6-11e8-bd1b-005056952d2b', '2018-05-21 11:27:51', '1', '2018-06-07 16:20:05', '1', '0', '0', '1', 'logistics_center', 'P_YMBJ2_P02', 'P_YMBJ2', '页面布局', '20', '100%', NULL, 'layout_a', NULL, '0', '0', '0', '1', NULL, NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('06244059-5ca7-11e8-bd1b-005056952d2b', '2018-05-21 11:28:26', '1', '2018-05-21 11:29:17', '1', '0', '0', '1', 'logistics_center', 'P_YMBJ2_P03', 'P_YMBJ2', '页面布局配置', '30', '100%', NULL, 'layout_a', NULL, '0', '0', '0', '1', '0', NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('1c0cd2b7-5ca7-11e8-bd1b-005056952d2b', '2018-05-21 11:29:03', '1', '2018-06-07 15:40:37', '1', '0', '0', '1', 'logistics_center', 'P_YMBJ2_P04', 'P_YMBJ2', '页面布局容器', '40', '100%', NULL, 'layout_b', NULL, '0', '0', '0', '0', NULL, NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('40674fd2-5ca7-11e8-bd1b-005056952d2b', '2018-05-21 11:30:04', '1', '2018-05-21 11:30:04', '1', '0', '0', '1', 'logistics_center', 'P_YMBJ2_P04_P01', 'P_YMBJ2', '页面元素', '50', '100%', NULL, 'layout_a', 'P_YMBJ2_P04', '0', '0', '0', '0', NULL, NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('57af9af9-5ca7-11e8-bd1b-005056952d2b', '2018-05-21 11:30:43', '1', '2018-05-21 11:30:43', '1', '0', '0', '1', 'logistics_center', 'P_YMBJ2_P04_P02', 'P_YMBJ2', '查询列表', '60', '100%', NULL, 'layout_a', 'P_YMBJ2_P04', '0', '0', '0', '0', NULL, NULL);

-- 页面布局2 - 页面配置
delete from setup_page_element where layout_id = 'P_YMBJ2_P01';

INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('16c7d3a9-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:36:04', 'ceshi', '2018-05-22 20:06:02', '10000', '0', '0', '3', 'ceshi', 'P_YMBJ2_P01_E01', '页面ID', 'P_YMBJ2_P01', '10', NULL, NULL, 'h_e_select', NULL, NULL, 'page_list', 'setup_page_layout', 'page_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('3f3694e0-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:37:11', 'ceshi', '2018-05-22 20:15:09', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_P01_E02', '宽度', 'P_YMBJ2_P01', '20', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('49d3bce1-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:37:29', 'ceshi', '2018-05-22 20:15:34', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_P01_E03', '高度', 'P_YMBJ2_P01', '30', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_height', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('3401f1ca-630b-11e8-9d0d-005056954f8e', '2018-05-29 14:40:40', '1', '2018-06-15 17:11:44', '1', '0', '0', '4', 'logistics_center', 'P_YMBJ2_P01_E04', '布局类型', 'P_YMBJ2_P01', '40', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', NULL, NULL, NULL, '50', NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('42acbfef-5ca9-11e8-bd1b-005056952d2b', '2018-05-21 11:44:27', 'ceshi', '2018-05-21 11:44:27', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_P01_B01', '查询', 'P_YMBJ2_P01', '1000', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-chaxun', NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('595d4018-5ca9-11e8-bd1b-005056952d2b', '2018-05-21 11:45:05', 'ceshi', '2018-05-21 11:45:21', 'ceshi', '0', '0', '2', 'ceshi', 'P_YMBJ2_P01_B02', '新增布局', 'P_YMBJ2_P01', '1010', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-xinzeng', NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('774423f1-5ca9-11e8-bd1b-005056952d2b', '2018-05-21 11:45:55', 'ceshi', '2018-05-21 11:45:55', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_P01_B03', '编辑', 'P_YMBJ2_P01', '1030', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-bianji', NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('9a7fb222-5ca9-11e8-bd1b-005056952d2b', '2018-05-21 11:46:54', 'ceshi', '2018-05-21 11:46:54', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_P01_B04', '删除', 'P_YMBJ2_P01', '1040', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-shanchu', NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('a5894e23-5ca9-11e8-bd1b-005056952d2b', '2018-05-21 11:47:13', 'ceshi', '2018-05-21 11:47:26', 'ceshi', '0', '0', '2', 'ceshi', 'P_YMBJ2_P01_B05', '预览', 'P_YMBJ2_P01', '1050', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-jiexi', NULL, NULL, NULL, NULL);

-- 页面布局2 - 页面布局
DELETE
FROM
	setup_page_table
WHERE
	layout_id = 'P_YMBJ2_P02';

INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('0a5caeef-6564-11e8-9d0d-005056954f8e', '2018-06-01 14:21:37', '1', '2018-06-01 14:21:37', '1', '0', '0', '1', 'logistics_center', 'PN100010', '编辑', 'P_YMBJ2_P02', '20', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', 'lt_btn');
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('10277899-6564-11e8-9d0d-005056954f8e', '2018-06-01 14:21:47', '1', '2018-06-01 14:33:52', '1', '0', '0', '1', 'logistics_center', 'PN100011', '启用', 'P_YMBJ2_P02', '10', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', 'lt_btn');
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('19617954-6564-11e8-9d0d-005056954f8e', '2018-06-01 14:22:03', '1', '2018-06-01 14:33:45', '1', '0', '0', '1', 'logistics_center', 'PN100012', '禁用', 'P_YMBJ2_P02', '30', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', 'lt_btn');
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('231f8ae8-6564-11e8-9d0d-005056954f8e', '2018-06-01 14:22:19', '1', '2018-06-01 14:34:08', '1', '0', '0', '1', 'logistics_center', 'PN100013', '重置', 'P_YMBJ2_P02', '6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', 'lt_btn');
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('3da7fd1a-6564-11e8-9d0d-005056954f8e', '2018-06-01 14:23:04', '1', '2018-06-01 14:34:14', '1', '0', '0', '1', 'logistics_center', 'PN100014', '删除', 'P_YMBJ2_P02', '60', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', 'lt_btn');
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('5bf9075b-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:38:00', 'ceshi', '2018-05-29 15:35:02', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P02_E01', '布局ID', 'P_YMBJ2_P02', '10', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('685a72c2-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:38:20', 'ceshi', '2018-05-29 15:34:48', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P02_E02', '所属页面', 'P_YMBJ2_P02', '20', NULL, NULL, NULL, 'view_setup_page_layout', 'page_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('71dd5992-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:38:36', 'ceshi', '2018-05-29 15:34:42', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P02_E03', '布局名称', 'P_YMBJ2_P02', '30', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('78adb215-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:38:48', 'ceshi', '2018-05-29 15:34:36', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P02_E04', '显示顺序', 'P_YMBJ2_P02', '40', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_seq', 'int', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('7d44176c-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:38:56', 'ceshi', '2018-05-29 15:34:31', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P02_E05', '宽度', 'P_YMBJ2_P02', '50', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('81f40755-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:39:03', 'ceshi', '2018-05-29 15:34:26', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P02_E06', '高度', 'P_YMBJ2_P02', '60', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_height', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('898271bf-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:39:16', 'ceshi', '2018-05-29 15:34:11', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P02_E07', '布局类型', 'P_YMBJ2_P02', '70', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('92a178b8-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:39:31', 'ceshi', '2018-05-29 15:34:54', '1', '0', '0', '5', 'ceshi', 'P_YMBJ2_P02_E08', '上级布局ID', 'P_YMBJ2_P02', '80', NULL, NULL, NULL, 'view_setup_page_layout', 'parent_layout_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('a1f048bd-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:39:57', 'ceshi', '2018-05-29 15:36:17', '1', '0', '0', '5', 'ceshi', 'P_YMBJ2_P02_E09', '配置按钮', 'P_YMBJ2_P02', '90', NULL, NULL, 'constant_y_n', 'view_setup_page_layout', 'btn_config', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('a89519ce-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:40:08', 'ceshi', '2018-05-29 15:36:27', '1', '0', '0', '5', 'ceshi', 'P_YMBJ2_P02_E10', '导出按钮', 'P_YMBJ2_P02', '100', NULL, NULL, 'constant_y_n', 'view_setup_page_layout', 'btn_export', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('af49f12b-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:40:19', 'ceshi', '2018-05-29 15:36:48', '1', '0', '0', '5', 'ceshi', 'P_YMBJ2_P02_E11', '导入按钮', 'P_YMBJ2_P02', '110', NULL, NULL, 'constant_y_n', 'view_setup_page_layout', 'btn_import', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('b5cebf8a-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:40:30', 'ceshi', '2018-05-29 15:37:01', '1', '0', '0', '5', 'ceshi', 'P_YMBJ2_P02_E12', '是否折叠', 'P_YMBJ2_P02', '120', NULL, NULL, 'constant_y_n', 'view_setup_page_layout', 'btn_fold', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);

-- 页面布局2 - 页面布局配置
DELETE
FROM
	setup_page_element
WHERE
	layout_id = 'P_YMBJ2_P03';

INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('c7427005-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:41:00', 'ceshi', '2018-05-28 14:12:24', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_P03_E01', '布局ID', 'P_YMBJ2_P03', '10', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d3c97a36-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:41:21', 'ceshi', '2018-06-12 13:38:11', 'kaihua.dai', '0', '0', '3', 'ceshi', 'P_YMBJ2_P03_E02', '布局名称', 'P_YMBJ2_P03', '20', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '50', NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d8fd5f3c-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:41:29', 'ceshi', '2018-05-21 11:41:29', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_P03_E03', '宽度', 'P_YMBJ2_P03', '30', NULL, NULL, 'h_e_input', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('f26db7d5-5ca8-11e8-bd1b-005056952d2b', '2018-05-21 11:42:12', 'ceshi', '2018-05-21 11:42:12', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_P03_E04', '高度', 'P_YMBJ2_P03', '40', NULL, NULL, 'h_e_input', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('041bdbf6-5ca9-11e8-bd1b-005056952d2b', '2018-05-21 11:42:42', 'ceshi', '2018-05-28 14:12:03', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_P03_E05', '布局类型', 'P_YMBJ2_P03', '50', NULL, NULL, 'h_e_select', NULL, '1', 'layout_type', 'setup_page_layout', 'layout_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('48509011-5cb6-11e8-bd1b-005056952d2b', '2018-05-21 13:17:40', 'ceshi', '2018-05-21 13:17:40', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_P03_B01', '新增页面元素', 'P_YMBJ2_P03', '100', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-xinzeng', NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('5c655acf-5cb6-11e8-bd1b-005056952d2b', '2018-05-21 13:18:13', 'ceshi', '2018-05-21 13:18:13', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_P03_B02', '新增查询列表', 'P_YMBJ2_P03', '110', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-xinzeng', NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('6bfc11e7-5cb6-11e8-bd1b-005056952d2b', '2018-05-21 13:18:40', 'ceshi', '2018-05-21 13:18:40', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_P03_B03', '编辑', 'P_YMBJ2_P03', '120', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-bianji', NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('77a38d6d-5cb6-11e8-bd1b-005056952d2b', '2018-05-21 13:18:59', 'ceshi', '2018-05-21 13:18:59', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_P03_B04', '删除', 'P_YMBJ2_P03', '130', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-shanchu', NULL, NULL, NULL, NULL);

-- 页面布局2 - 页面布局容器
-- 页面布局2 - 页面元素
DELETE
FROM
	setup_page_table
WHERE
	layout_id = 'P_YMBJ2_P04_P01';

INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d388f3d3-6561-11e8-9d0d-005056954f8e', '2018-06-01 14:05:46', '1', '2018-06-01 14:09:45', '1', '0', '0', '2', 'logistics_center', 'PN100008', '元素说明', 'P_YMBJ2_P04_P01', '180', NULL, NULL, NULL, 'view_setup_page_element', 'element_title', 'varchar', NULL, NULL, NULL, NULL, '0', NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('e90240e8-6561-11e8-9d0d-005056954f8e', '2018-06-01 14:06:23', '1', '2018-06-01 14:36:32', '1', '0', '0', '3', 'logistics_center', 'PN100009', '字符过滤', 'P_YMBJ2_P04_P01', '190', NULL, NULL, NULL, 'view_setup_page_element', 'is_filt_display', 'varchar', NULL, NULL, NULL, NULL, '0', NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('f0faa593-5cb8-11e8-bd1b-005056952d2b', '2018-05-21 13:36:42', 'ceshi', '2018-05-29 16:41:09', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E01', '元素ID', 'P_YMBJ2_P04_P01', '10', NULL, NULL, NULL, 'view_setup_page_element', 'element_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('f8d4c93a-5cb8-11e8-bd1b-005056952d2b', '2018-05-21 13:36:55', 'ceshi', '2018-05-29 16:41:04', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E02', '元素名称', 'P_YMBJ2_P04_P01', '20', NULL, NULL, NULL, 'view_setup_page_element', 'element_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('ffe0bbb2-5cb8-11e8-bd1b-005056952d2b', '2018-05-21 13:37:07', 'ceshi', '2018-05-29 16:40:58', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E03', '显示顺序', 'P_YMBJ2_P04_P01', '30', NULL, NULL, NULL, 'view_setup_page_element', 'element_seq', 'int', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('067be3dd-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:37:18', 'ceshi', '2018-05-29 16:40:54', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E04', '宽度', 'P_YMBJ2_P04_P01', '40', NULL, NULL, NULL, 'view_setup_page_element', 'element_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('0c875591-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:37:28', 'ceshi', '2018-05-29 16:40:49', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E05', '高度', 'P_YMBJ2_P04_P01', '50', NULL, NULL, NULL, 'view_setup_page_element', 'element_height', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('158478e7-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:37:43', 'ceshi', '2018-05-29 17:49:33', '1', '0', '0', '6', 'ceshi', 'P_YMBJ2_P04_P01_E06', '元素类型', 'P_YMBJ2_P04_P01', '60', NULL, NULL, NULL, 'view_setup_page_element', 'element_type_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('1dc82897-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:37:57', 'ceshi', '2018-05-29 16:40:38', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E07', '初始值', 'P_YMBJ2_P04_P01', '70', NULL, NULL, NULL, 'view_setup_page_element', 'default_value', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('2ef95139-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:38:26', 'ceshi', '2018-05-29 16:32:58', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E08', '只读', 'P_YMBJ2_P04_P01', '80', NULL, NULL, NULL, 'view_setup_page_element', 'element_disabled_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('382b16ed-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:38:41', 'ceshi', '2018-05-29 16:40:32', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E09', '显示格式', 'P_YMBJ2_P04_P01', '90', NULL, NULL, NULL, 'view_setup_page_element', 'element_format', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('9141bf37-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:41:11', 'ceshi', '2018-06-01 13:55:08', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E10', '新增只读', 'P_YMBJ2_P04_P01', '100', NULL, NULL, NULL, 'view_setup_page_element', 'add_readonly_display', 'varchar', NULL, NULL, NULL, NULL, '1', NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('9861d44f-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:41:23', 'ceshi', '2018-06-01 13:55:14', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E11', '修改只读', 'P_YMBJ2_P04_P01', '110', NULL, NULL, NULL, 'view_setup_page_element', 'update_readonly_display', 'varchar', NULL, NULL, NULL, NULL, '1', NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('a8fb9a80-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:41:50', 'ceshi', '2018-05-29 17:42:33', '1', '0', '0', '5', 'ceshi', 'P_YMBJ2_P04_P01_E12', '按钮快捷键', 'P_YMBJ2_P04_P01', '120', NULL, NULL, NULL, 'view_setup_page_element', 'button_click_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('aee8df18-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:42:00', 'ceshi', '2018-05-29 16:32:16', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E13', '必输', 'P_YMBJ2_P04_P01', '130', NULL, NULL, NULL, 'view_setup_page_element', 'not_null_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('b508aa60-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:42:11', 'ceshi', '2018-06-01 13:55:22', '1', '0', '0', '5', 'ceshi', 'P_YMBJ2_P04_P01_E14', '新增必输', 'P_YMBJ2_P04_P01', '140', NULL, NULL, NULL, 'view_setup_page_element', 'add_not_null_display', 'varchar', NULL, NULL, NULL, NULL, '1', NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('bb1091ff-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:42:21', 'ceshi', '2018-06-01 13:55:01', '1', '0', '0', '5', 'ceshi', 'P_YMBJ2_P04_P01_E15', '修改必输', 'P_YMBJ2_P04_P01', '150', NULL, NULL, NULL, 'view_setup_page_element', 'update_not_null_display', 'varchar', NULL, NULL, NULL, NULL, '1', NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('c1ced053-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:42:32', 'ceshi', '2018-05-29 17:46:59', '1', '0', '0', '6', 'ceshi', 'P_YMBJ2_P04_P01_E16', '按钮位置', 'P_YMBJ2_P04_P01', '160', NULL, NULL, NULL, 'view_setup_page_element', 'button_set_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('cd038b2b-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:42:51', 'ceshi', '2018-05-29 16:11:07', '1', '0', '0', '4', 'ceshi', 'P_YMBJ2_P04_P01_E17', '按钮图片', 'P_YMBJ2_P04_P01', '170', NULL, NULL, NULL, 'view_setup_page_element', 'button_picture', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('c0826ac4-6314-11e8-9d0d-005056954f8e', '2018-05-29 15:49:01', '1', '2018-05-29 16:33:10', '1', '0', '0', '4', 'logistics_center', 'P_YMBJ2_P04_P01_E18', '隐藏', 'P_YMBJ2_P04_P01', '180', NULL, NULL, NULL, 'view_setup_page_element', 'element_hide_display', 'varchar', NULL, NULL, NULL, NULL, '0', NULL);

-- 页面布局2-查询列表


DELETE
FROM
	setup_page_table
WHERE
	layout_id = 'P_YMBJ2_P04_P02';

INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('edf7139f-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:43:46', 'ceshi', '2018-05-29 16:06:07', '1', '0', '0', '6', 'ceshi', 'P_YMBJ2_P04_P02_E01', '列ID', 'P_YMBJ2_P04_P02', '10', NULL, NULL, NULL, 'view_setup_page_table', 'column_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('f3a01b7d-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:43:56', 'ceshi', '2018-05-29 16:06:12', '1', '0', '0', '9', 'ceshi', 'P_YMBJ2_P04_P02_E02', '列名', 'P_YMBJ2_P04_P02', '20', NULL, NULL, NULL, 'view_setup_page_table', 'column_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('f8da7167-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:44:04', 'ceshi', '2018-05-29 16:06:01', '1', '0', '0', '6', 'ceshi', 'P_YMBJ2_P04_P02_E03', '显示顺序', 'P_YMBJ2_P04_P02', '30', NULL, NULL, NULL, 'view_setup_page_table', 'column_seq', 'int', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('fdb48939-5cb9-11e8-bd1b-005056952d2b', '2018-05-21 13:44:12', 'ceshi', '2018-05-29 16:05:54', '1', '0', '0', '6', 'ceshi', 'P_YMBJ2_P04_P02_E04', '宽度', 'P_YMBJ2_P04_P02', '40', NULL, NULL, NULL, 'view_setup_page_table', 'column_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('04ceeaf6-5cba-11e8-bd1b-005056952d2b', '2018-05-21 13:44:24', 'ceshi', '2018-05-29 16:05:48', '1', '0', '0', '6', 'ceshi', 'P_YMBJ2_P04_P02_E05', '显示格式', 'P_YMBJ2_P04_P02', '50', NULL, NULL, NULL, 'view_setup_page_table', 'column_format', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d2215023-6314-11e8-9d0d-005056954f8e', '2018-05-29 15:49:31', '1', '2018-05-29 16:07:46', '1', '0', '0', '5', 'logistics_center', 'P_YMBJ2_P04_P02_E06', '隐藏', 'P_YMBJ2_P04_P02', '60', NULL, NULL, NULL, 'view_setup_page_table', 'column_hide_display', 'varchar', NULL, NULL, NULL, NULL, '0', NULL);


-- 页面记录添加
DELETE FROM setup_page WHERE page_id = 'P_YMBJ2_ADD';
INSERT INTO `setup_page` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `page_id`, `page_name`, `page_width`, `page_height`, `page_type`, `parent_page_id`, `page_url`) VALUES ('1c8ea79c-5ccb-11e8-bd1b-005056952d2b', '2018-05-21 15:46:46', '1', '2018-05-21 15:46:46', '1', '0', '0', '1', 'logistics_center', 'P_YMBJ2_ADD', '页面布局-新增', '660', NULL, 'page_open', 'P_YMBJ2', NULL);
DELETE FROM setup_page WHERE page_id = 'P_YMBJ2_ADDELE';
INSERT INTO `setup_page` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `page_id`, `page_name`, `page_width`, `page_height`, `page_type`, `parent_page_id`, `page_url`) VALUES ('8165765f-5cd7-11e8-bd1b-005056952d2b', '2018-05-21 17:15:29', '1', '2018-05-21 17:18:01', '1', '0', '0', '2', 'logistics_center', 'P_YMBJ2_ADDELE', '页面布局-新增页面元素', '660', NULL, 'page_open', 'P_YMBJ2', NULL);
DELETE FROM setup_page WHERE page_id = 'P_YMBJ2_ADDTABLE';
INSERT INTO `setup_page` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `page_id`, `page_name`, `page_width`, `page_height`, `page_type`, `parent_page_id`, `page_url`) VALUES ('92afbbff-5cd7-11e8-bd1b-005056952d2b', '2018-05-21 17:15:58', '1', '2018-05-21 17:17:45', '1', '0', '0', '2', 'logistics_center', 'P_YMBJ2_ADDTABLE', '页面布局-新增查询列表', '660', NULL, 'page_open', 'P_YMBJ2', NULL);


-- 页面布局2-新增
DELETE
FROM
	setup_page_layout
WHERE
	page_id = 'P_YMBJ2_ADD';
	
INSERT INTO  `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('c1df281f-5ccb-11e8-bd1b-005056952d2b', '2018-05-21 15:51:23', 'ceshi', '2018-05-21 16:09:14', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADD_P01', 'P_YMBJ2_ADD', '新增布局', '10', '100%', NULL, 'layout_a', NULL, '0', '0', '0', '0', '0', NULL);
DELETE
FROM
	setup_page_element
WHERE
	layout_id = 'P_YMBJ2_ADD_P01';

INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('66bf4a34-5cce-11e8-bd1b-005056952d2b', '2018-05-21 16:10:19', 'ceshi', '2018-05-30 17:59:30', '1', '0', '0', '3', 'ceshi', 'P_YMBJ2_ADD_P01_E01', '布局ID', 'P_YMBJ2_ADD_P01', '10', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_layout', 'layout_id', 'varchar', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('774ee17e-5cce-11e8-bd1b-005056952d2b', '2018-05-21 16:10:46', 'ceshi', '2018-05-23 14:40:54', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADD_P01_E02', '所属页面', 'P_YMBJ2_ADD_P01', '20', '50%', NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'page_id', 'varchar', NULL, NULL, NULL, NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('87d28698-5cce-11e8-bd1b-005056952d2b', '2018-05-21 16:11:14', 'ceshi', '2018-05-23 14:40:30', '10000', '0', '0', '3', 'ceshi', 'P_YMBJ2_ADD_P01_E03', '布局名称', 'P_YMBJ2_ADD_P01', '30', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_layout', 'layout_name', 'varchar', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('935bac48-5cce-11e8-bd1b-005056952d2b', '2018-05-21 16:11:34', 'ceshi', '2018-05-23 14:40:04', '10000', '0', '0', '3', 'ceshi', 'P_YMBJ2_ADD_P01_E04', '显示顺序', 'P_YMBJ2_ADD_P01', '40', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_layout', 'layout_seq', 'int', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('c91dfcc0-5cce-11e8-bd1b-005056952d2b', '2018-05-21 16:13:04', 'ceshi', '2018-05-23 14:40:13', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADD_P01_E05', '宽度', 'P_YMBJ2_ADD_P01', '50', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_layout', 'layout_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('342ab49f-5ccf-11e8-bd1b-005056952d2b', '2018-05-21 16:16:03', 'ceshi', '2018-05-23 14:39:50', '10000', '0', '0', '3', 'ceshi', 'P_YMBJ2_ADD_P01_E07', '高度', 'P_YMBJ2_ADD_P01', '70', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_layout', 'layout_height', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('947f71c5-5cd6-11e8-bd1b-005056952d2b', '2018-05-21 17:08:51', 'ceshi', '2018-05-23 14:41:52', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADD_P01_E09', '布局类型', 'P_YMBJ2_ADD_P01', '90', '50%', NULL, 'h_e_select', NULL, NULL, 'layout_type', 'setup_page_layout', 'layout_type', 'varchar', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('9fc984dd-5cd6-11e8-bd1b-005056952d2b', '2018-05-21 17:09:10', 'ceshi', '2018-05-23 14:39:36', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADD_P01_E10', '上级布局', 'P_YMBJ2_ADD_P01', '100', '50%', NULL, 'h_e_select', NULL, NULL, NULL, 'setup_page_layout', 'parent_layout_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d0cebe20-5cd6-11e8-bd1b-005056952d2b', '2018-05-21 17:10:33', 'ceshi', '2018-05-23 14:44:04', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADD_P01_E11', '配置按钮', 'P_YMBJ2_ADD_P01', '110', '50%', NULL, 'h_e_select', NULL, NULL, 'constant_y_n', 'setup_page_layout', 'button_flag1', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('dc1b2910-5cd6-11e8-bd1b-005056952d2b', '2018-05-21 17:10:52', 'ceshi', '2018-05-23 14:43:31', '10000', '0', '0', '3', 'ceshi', 'P_YMBJ2_ADD_P01_E12', '导出按钮', 'P_YMBJ2_ADD_P01', '120', '50%', NULL, 'h_e_select', NULL, NULL, 'constant_y_n', 'setup_page_layout', 'button_flag2', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('e60e2210-5cd6-11e8-bd1b-005056952d2b', '2018-05-21 17:11:08', 'ceshi', '2018-05-23 14:43:39', '10000', '0', '0', '3', 'ceshi', 'P_YMBJ2_ADD_P01_E13', '导入按钮', 'P_YMBJ2_ADD_P01', '130', '50%', NULL, 'h_e_select', NULL, NULL, 'constant_y_n', 'setup_page_layout', 'button_flag3', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('ef5a66b4-5cd6-11e8-bd1b-005056952d2b', '2018-05-21 17:11:24', 'ceshi', '2018-05-23 15:44:05', '10000', '0', '0', '4', 'ceshi', 'P_YMBJ2_ADD_P01_E14', '是否折叠', 'P_YMBJ2_ADD_P01', '140', '50%', NULL, 'h_e_select', NULL, NULL, 'constant_y_n', 'setup_page_layout', 'button_flag4', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('23139165-5cd7-11e8-bd1b-005056952d2b', '2018-05-21 17:12:51', 'ceshi', '2018-05-21 17:12:51', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADD_P01_B01', '保存', 'P_YMBJ2_ADD_P01', '510', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('2b6f7a57-5cd7-11e8-bd1b-005056952d2b', '2018-05-21 17:13:05', 'ceshi', '2018-05-21 17:13:05', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADD_P01_B02', '重置', 'P_YMBJ2_ADD_P01', '520', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('3339cf10-5cd7-11e8-bd1b-005056952d2b', '2018-05-21 17:13:18', 'ceshi', '2018-05-21 17:13:18', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADD_P01_B03', '关闭', 'P_YMBJ2_ADD_P01', '530', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);

-- 页面布局2-新增页面元素
DELETE
FROM
	setup_page_layout
WHERE
	page_id = 'P_YMBJ2_ADDELE';

INSERT INTO `setup_page_layout` (
	`id`,
	`create_time`,
	`create_by`,
	`update_time`,
	`update_by`,
	`is_deleted`,
	`is_disabled`,
	`version`,
	`pwr_org`,
	`layout_id`,
	`page_id`,
	`layout_name`,
	`layout_seq`,
	`layout_width`,
	`layout_height`,
	`layout_type`,
	`parent_layout_id`,
	`button_flag1`,
	`button_flag2`,
	`button_flag3`,
	`button_flag4`,
	`button_flag5`,
	`is_hide`
)
VALUES
	(
		'000bb557-5cd8-11e8-bd1b-005056952d2b',
		'2018-05-21 17:19:01',
		'ceshi',
		'2018-05-21 17:19:01',
		'ceshi',
		'0',
		'0',
		'1',
		'ceshi',
		'P_YMBJ2_ADDELE_P01',
		'P_YMBJ2_ADDELE',
		'新增页面元素',
		'10',
		'100%',
		NULL,
		'layout_a',
		NULL,
		'0',
		'0',
		'0',
		'0',
		NULL,
		NULL
	);

DELETE
FROM
	setup_page_element
WHERE
	layout_id = 'P_YMBJ2_ADDELE_P01';


INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('12783f3f-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:19:32', 'ceshi', '2018-06-01 10:55:56', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E01', '元素ID', 'P_YMBJ2_ADDELE_P01', '10', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'element_id', 'varchar', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('1ba3461d-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:19:48', 'ceshi', '2018-05-23 18:06:46', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E02', '元素名称', 'P_YMBJ2_ADDELE_P01', '20', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'element_name', 'varchar', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('2870b9e0-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:20:09', 'ceshi', '2018-05-23 18:06:54', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E03', '所属布局', 'P_YMBJ2_ADDELE_P01', '30', '50%', NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_element', 'layout_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('34a7fe4b-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:20:30', 'ceshi', '2018-05-23 18:07:02', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E04', '显示顺序', 'P_YMBJ2_ADDELE_P01', '40', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'element_seq', 'int', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('bbab3d56-6473-11e8-9d0d-005056954f8e', '2018-05-31 09:41:26', '1', '2018-06-01 13:42:17', '1', '0', '0', '2', 'logistics_center', 'P_YMBJ2_ADDELE_P01_E23', '元素说明', 'P_YMBJ2_ADDELE_P01', '45', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'element_title', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', NULL, NULL, '0', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('41bebc93-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:20:52', 'ceshi', '2018-05-23 18:07:09', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E05', '宽度', 'P_YMBJ2_ADDELE_P01', '50', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'element_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('5e6d357a-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:21:40', 'ceshi', '2018-05-23 18:06:06', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E07', '高度', 'P_YMBJ2_ADDELE_P01', '70', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'element_height', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('7fde04b4-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:22:36', 'ceshi', '2018-05-23 18:02:31', '10000', '0', '0', '4', 'ceshi', 'P_YMBJ2_ADDELE_P01_E09', '元素类型', 'P_YMBJ2_ADDELE_P01', '90', '50%', NULL, 'h_e_select', NULL, NULL, 'html_element', 'setup_page_element', 'element_type', 'varchar', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('874fd3c8-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:22:48', 'ceshi', '2018-05-23 18:07:31', '10000', '0', '0', '3', 'ceshi', 'P_YMBJ2_ADDELE_P01_E10', '初始值', 'P_YMBJ2_ADDELE_P01', '100', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'default_value', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('8fda591d-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:23:03', 'ceshi', '2018-05-23 18:06:19', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E11', '显示格式', 'P_YMBJ2_ADDELE_P01', '110', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'element_format', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('40b42786-5e58-11e8-bd1b-005056952d2b', '2018-05-23 15:09:37', 'ceshi', '2018-05-23 18:11:24', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E22', '最大长度', 'P_YMBJ2_ADDELE_P01', '115', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'column_len', 'int', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('a4de2ada-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:23:38', 'ceshi', '2018-05-23 18:07:19', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E12', '按钮快捷键', 'P_YMBJ2_ADDELE_P01', '120', '50%', NULL, 'h_e_select', NULL, '1', 'button_click', 'setup_page_element', 'button_click', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('ea3bfbf2-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:25:34', 'ceshi', '2018-05-23 18:05:25', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E13', '按钮位置', 'P_YMBJ2_ADDELE_P01', '130', '50%', NULL, 'h_e_select', NULL, '1', 'button_set', 'setup_page_element', 'button_set', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('f6fe06a9-5cd8-11e8-bd1b-005056952d2b', '2018-05-21 17:25:56', 'ceshi', '2018-05-23 18:05:15', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E14', '按钮图片', 'P_YMBJ2_ADDELE_P01', '140', '50%', NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_element', 'button_picture', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('f0f2a688-5e54-11e8-bd1b-005056952d2b', '2018-05-23 14:45:54', 'ceshi', '2018-05-23 18:02:17', '10000', '0', '0', '3', 'ceshi', 'P_YMBJ2_ADDELE_P01_E21', '是否隐藏', 'P_YMBJ2_ADDELE_P01', '145', '50%', NULL, 'h_e_select', NULL, NULL, 'constant_y_n', 'setup_page_element', 'element_hide', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('007f5c0e-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:26:12', 'ceshi', '2018-05-23 18:05:05', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E15', '只读', 'P_YMBJ2_ADDELE_P01', '150', '50%', NULL, 'h_e_switch', NULL, NULL, NULL, 'setup_page_element', 'element_disabled', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('0c2aba23-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:26:31', 'ceshi', '2018-05-30 18:00:59', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E16', '新增只读', 'P_YMBJ2_ADDELE_P01', '160', '50%', NULL, 'h_e_switch', NULL, NULL, NULL, 'setup_page_element', 'add_readonly', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('20c71108-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:27:06', 'ceshi', '2018-05-30 18:01:13', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E17', '修改只读', 'P_YMBJ2_ADDELE_P01', '170', '50%', NULL, 'h_e_switch', NULL, NULL, NULL, 'setup_page_element', 'update_readonly', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('2892da1a-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:27:19', 'ceshi', '2018-05-23 18:04:53', '10000', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E18', '必输', 'P_YMBJ2_ADDELE_P01', '180', '50%', NULL, 'h_e_switch', NULL, NULL, NULL, 'setup_page_element', 'not_null', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('3305f51f-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:27:36', 'ceshi', '2018-05-30 18:01:24', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E19', '新增必输', 'P_YMBJ2_ADDELE_P01', '190', '50%', NULL, 'h_e_switch', NULL, NULL, NULL, 'setup_page_element', 'add_not_null', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('3c90d2f9-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:27:52', 'ceshi', '2018-05-30 18:01:36', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_E20', '修改必输', 'P_YMBJ2_ADDELE_P01', '200', '50%', NULL, 'h_e_switch', NULL, NULL, NULL, 'setup_page_element', 'update_not_null', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('ea30a557-6473-11e8-9d0d-005056954f8e', '2018-05-31 09:42:44', '1', '2018-06-01 13:40:32', '1', '0', '0', '2', 'logistics_center', 'P_YMBJ2_ADDELE_P01_E24', '字符过滤', 'P_YMBJ2_ADDELE_P01', '240', '50%', NULL, 'h_e_switch', NULL, NULL, NULL, 'setup_page_element', 'is_filt', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', NULL, NULL, '0', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('4b0e4a22-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:28:17', 'ceshi', '2018-05-21 17:28:17', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADDELE_P01_B01', '保存', 'P_YMBJ2_ADDELE_P01', '510', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('52397180-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:28:29', 'ceshi', '2018-05-21 17:28:29', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADDELE_P01_B02', '重置', 'P_YMBJ2_ADDELE_P01', '520', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('5a4671e6-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:28:42', 'ceshi', '2018-05-21 17:28:53', 'ceshi', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDELE_P01_B03', '关闭', 'P_YMBJ2_ADDELE_P01', '530', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);

-- 页面布局2 - 新增查询列表
DELETE
FROM
	setup_page_layout
WHERE
	page_id = 'P_YMBJ2_ADDTABLE';

INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('7d21a462-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:29:41', 'ceshi', '2018-05-21 17:29:41', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADDTABLE_P1', 'P_YMBJ2_ADDTABLE', '新增查询列表', '10', '100%', NULL, 'layout_a', NULL, '0', '0', '0', '0', NULL, NULL);

DELETE
FROM
	setup_page_element
WHERE
	layout_id = 'P_YMBJ2_ADDTABLE_P1';

INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('9085fac3-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:30:13', 'ceshi', '2018-06-01 10:56:08', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_E01', '列ID', 'P_YMBJ2_ADDTABLE_P1', '10', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_table', 'column_id', 'varchar', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('e4cc3eea-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:32:35', 'ceshi', '2018-05-28 18:53:42', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_E02', '列名', 'P_YMBJ2_ADDTABLE_P1', '20', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_table', 'column_name', 'varchar', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('ecf2a5e6-5cd9-11e8-bd1b-005056952d2b', '2018-05-21 17:32:48', 'ceshi', '2018-05-28 18:53:29', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_E03', '所属布局', 'P_YMBJ2_ADDTABLE_P1', '30', '50%', NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_table', 'layout_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('8965c5bc-5cda-11e8-bd1b-005056952d2b', '2018-05-21 17:37:11', 'ceshi', '2018-05-28 18:53:14', '1', '0', '0', '3', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_E04', '显示顺序', 'P_YMBJ2_ADDTABLE_P1', '40', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_table', 'column_seq', 'int', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('9e708b0f-5cda-11e8-bd1b-005056952d2b', '2018-05-21 17:37:46', 'ceshi', '2018-06-11 16:21:01', '2', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_E05', '宽度', 'P_YMBJ2_ADDTABLE_P1', '50', '50%', NULL, 'h_e_input', NULL, '0', NULL, 'setup_page_table', 'column_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('e10b1e0f-5cda-11e8-bd1b-005056952d2b', '2018-05-21 17:39:38', 'ceshi', '2018-05-28 18:52:50', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_E07', '显示格式', 'P_YMBJ2_ADDTABLE_P1', '70', '50%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_table', 'column_format', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('04ed73a5-5e55-11e8-bd1b-005056952d2b', '2018-05-23 14:46:28', 'ceshi', '2018-05-28 18:52:38', '1', '0', '0', '2', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_E08', '是否隐藏', 'P_YMBJ2_ADDTABLE_P1', '80', '50%', NULL, 'h_e_select', NULL, NULL, 'constant_y_n', 'setup_page_table', 'column_hide', 'tinyint', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('e5946e73-6475-11e8-9d0d-005056954f8e', '2018-05-31 09:56:56', '1', '2018-06-01 11:20:19', '1', '0', '0', '2', 'logistics_center', 'P_YMBJ2_ADDTABLE_P1_E09', '列表类型', 'P_YMBJ2_ADDTABLE_P1', '90', '50%', NULL, 'h_e_select', NULL, NULL, 'list_type', 'setup_page_table', 'list_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', NULL, NULL, '0', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('92b3a87a-5cdb-11e8-bd1b-005056952d2b', '2018-05-21 17:44:36', 'ceshi', '2018-05-21 17:44:36', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_B01', '保存', 'P_YMBJ2_ADDTABLE_P1', '510', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('9b26b9bd-5cdb-11e8-bd1b-005056952d2b', '2018-05-21 17:44:50', 'ceshi', '2018-05-21 17:44:50', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_B02', '重置', 'P_YMBJ2_ADDTABLE_P1', '520', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('a1cd7e78-5cdb-11e8-bd1b-005056952d2b', '2018-05-21 17:45:01', 'ceshi', '2018-05-21 17:45:01', 'ceshi', '0', '0', '1', 'ceshi', 'P_YMBJ2_ADDTABLE_P1_B03', '关闭', 'P_YMBJ2_ADDTABLE_P1', '530', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);


-- 页面布局 SQL 脚本同步
DELETE FROM
	setup_page_layout
WHERE
	page_id = 'P_YMBJ';

INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('d3de5ff2-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', '1', '2018-06-04 19:45:49', '1', '0', '0', '1', 'logistics_center', 'PN100439', 'P_YMBJ', '页面配置', '10', '100%', NULL, 'layout_a', NULL, '0', '0', '0', '1', NULL, NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('d3de61b1-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', '1', '2018-06-04 19:45:49', '1', '0', '0', '1', 'logistics_center', 'PN100449', 'P_YMBJ', '页面布局', '20', '100%', NULL, 'layout_a', NULL, '0', '0', '0', '1', NULL, NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('d3de4f8f-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', '1', '2018-06-04 19:45:49', '1', '0', '0', '1', 'logistics_center', 'PN100400', 'P_YMBJ', '页面布局配置', '30', '100%', NULL, 'layout_a', NULL, '0', '0', '0', '1', '0', NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('d3de52eb-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', '1', '2018-06-04 19:45:49', '1', '0', '0', '1', 'logistics_center', 'PN100410', 'P_YMBJ', '页面布局容器', '40', '100%', NULL, 'layout_b', NULL, '0', '0', '0', '0', NULL, NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('d3de54ac-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', '1', '2018-06-04 19:45:49', '1', '0', '0', '1', 'logistics_center', 'PN100411', 'P_YMBJ', '页面元素', '50', '100%', NULL, 'layout_a', 'P_YMBJ2_P04', '0', '0', '0', '0', NULL, NULL);
INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('d3de5619-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', '1', '2018-06-04 19:45:49', '1', '0', '0', '1', 'logistics_center', 'PN100432', 'P_YMBJ', '查询列表', '60', '100%', NULL, 'layout_a', 'P_YMBJ2_P04', '0', '0', '0', '0', NULL, NULL);

-- 页面布局 - 页面配置
DELETE
FROM
	setup_page_element
WHERE
	layout_id = 'PN100439';

INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d2fe1919-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:48', 'ceshi', '2018-06-04 19:45:48', 'ceshi', '0', '0', '1', 'ceshi', 'PN100448', '页面ID', 'PN100439', '10', NULL, NULL, 'h_e_select', NULL, NULL, 'page_list', 'setup_page_layout', 'page_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d2fe1118-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:48', 'ceshi', '2018-06-04 19:45:48', 'ceshi', '0', '0', '1', 'ceshi', 'PN100447', '宽度', 'PN100439', '20', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d2fe0c5a-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:48', 'ceshi', '2018-06-04 19:45:48', 'ceshi', '0', '0', '1', 'ceshi', 'PN100445', '高度', 'PN100439', '30', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_height', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d2fe0ecc-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:48', '1', '2018-06-04 19:45:48', '1', '0', '0', '1', 'logistics_center', 'PN100446', '布局类型', 'PN100439', '40', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d2fdfc1b-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:48', 'ceshi', '2018-06-04 19:45:48', 'ceshi', '0', '0', '1', 'ceshi', 'PN100440', '查询', 'PN100439', '1000', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-chaxun', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d2fe01b3-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:48', 'ceshi', '2018-06-04 19:45:48', 'ceshi', '0', '0', '1', 'ceshi', 'PN100441', '新增布局', 'PN100439', '1010', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-xinzeng', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d2fe041e-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:48', 'ceshi', '2018-06-04 19:45:48', 'ceshi', '0', '0', '1', 'ceshi', 'PN100442', '编辑', 'PN100439', '1030', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-bianji', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d2fe0857-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:48', 'ceshi', '2018-06-04 19:45:48', 'ceshi', '0', '0', '1', 'ceshi', 'PN100443', '删除', 'PN100439', '1040', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-shanchu', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d2fe0a6c-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:48', 'ceshi', '2018-06-04 19:45:48', 'ceshi', '0', '0', '1', 'ceshi', 'PN100444', '预览', 'PN100439', '1050', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-jiexi', NULL, NULL, NULL, NULL);


-- 页面布局 - 页面布局
DELETE
FROM
	setup_page_table
WHERE
	layout_id = 'PN100449';

INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d65621-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100450', '布局ID', 'PN100449', '10', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_id', 'varchar', 'desc', '3', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d65ac7-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100451', '所属页面', 'PN100449', '20', NULL, NULL, NULL, 'view_setup_page_layout', 'page_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d65cb0-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100452', '布局名称', 'PN100449', '30', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d65e36-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100453', '显示顺序', 'PN100449', '40', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_seq', 'int', 'asc', '1', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d65fc6-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100454', '宽度', 'PN100449', '50', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d66148-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100455', '高度', 'PN100449', '60', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_height', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d662d5-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100456', '布局类型', 'PN100449', '70', NULL, NULL, NULL, 'view_setup_page_layout', 'layout_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d66458-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100457', '上级布局ID', 'PN100449', '80', NULL, NULL, NULL, 'view_setup_page_layout', 'parent_layout_id', 'varchar', 'asc', '2', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d665c0-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100458', '配置按钮', 'PN100449', '90', NULL, NULL, 'constant_y_n', 'view_setup_page_layout', 'btn_config', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d66780-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100459', '导出按钮', 'PN100449', '100', NULL, NULL, 'constant_y_n', 'view_setup_page_layout', 'btn_export', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d668da-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100460', '导入按钮', 'PN100449', '110', NULL, NULL, 'constant_y_n', 'view_setup_page_layout', 'btn_import', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d3d66adf-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:49', 'ceshi', '2018-06-04 19:45:49', 'ceshi', '0', '0', '1', 'ceshi', 'PN100461', '是否折叠', 'PN100449', '120', NULL, NULL, 'constant_y_n', 'view_setup_page_layout', 'btn_fold', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);


-- 页面布局- 页面布局配置
DELETE
FROM
	setup_page_element
WHERE
	layout_id = 'PN100400';

INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d0849374-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:44', 'ceshi', '2018-06-04 19:45:44', 'ceshi', '0', '0', '1', 'ceshi', 'PN100407', '布局ID', 'PN100400', '10', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d0849564-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:44', 'ceshi', '2018-06-04 19:45:44', 'ceshi', '0', '0', '1', 'ceshi', 'PN100408', '布局名称', 'PN100400', '20', NULL, NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_layout', 'layout_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d0848fb9-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:44', 'ceshi', '2018-06-04 19:45:44', 'ceshi', '0', '0', '1', 'ceshi', 'PN100405', '宽度', 'PN100400', '30', NULL, NULL, 'h_e_input', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d08491a1-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:44', 'ceshi', '2018-06-04 19:45:44', 'ceshi', '0', '0', '1', 'ceshi', 'PN100406', '高度', 'PN100400', '40', NULL, NULL, 'h_e_input', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d0849aeb-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:44', 'ceshi', '2018-06-04 19:45:44', 'ceshi', '0', '0', '1', 'ceshi', 'PN100409', '布局类型', 'PN100400', '50', NULL, NULL, 'h_e_select', NULL, '1', 'layout_type', 'setup_page_layout', 'layout_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d0848341-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:44', 'ceshi', '2018-06-04 19:45:44', 'ceshi', '0', '0', '1', 'ceshi', 'PN100401', '新增页面元素', 'PN100400', '100', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-xinzeng', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d08488f9-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:44', 'ceshi', '2018-06-04 19:45:44', 'ceshi', '0', '0', '1', 'ceshi', 'PN100402', '新增查询列表', 'PN100400', '110', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-xinzeng', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d0848bcb-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:44', 'ceshi', '2018-06-04 19:45:44', 'ceshi', '0', '0', '1', 'ceshi', 'PN100403', '编辑', 'PN100400', '120', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-bianji', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d0848dd3-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:44', 'ceshi', '2018-06-04 19:45:44', 'ceshi', '0', '0', '1', 'ceshi', 'PN100404', '删除', 'PN100400', '130', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', 'icon-shanchu', NULL, NULL, NULL, NULL);

-- 页面布局 - 页面布局容器

-- 页面布局 - 页面元素

DELETE
FROM
	setup_page_table
WHERE
	layout_id = 'PN100411';

INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e5ed54-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100412', '宽度', 'PN100411', '40', NULL, NULL, NULL, 'view_setup_page_element', 'element_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e5f212-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100413', '高度', 'PN100411', '50', NULL, NULL, NULL, 'view_setup_page_element', 'element_height', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e5f3d1-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100414', '元素类型', 'PN100411', '60', NULL, NULL, NULL, 'view_setup_page_element', 'element_type_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e5f545-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100415', '初始值', 'PN100411', '70', NULL, NULL, NULL, 'view_setup_page_element', 'default_value', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e5f6a9-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100416', '只读', 'PN100411', '80', NULL, NULL, NULL, 'view_setup_page_element', 'element_disabled_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e5f809-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100417', '显示格式', 'PN100411', '90', NULL, NULL, NULL, 'view_setup_page_element', 'element_format', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e5fc21-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100418', '新增只读', 'PN100411', '100', NULL, NULL, NULL, 'view_setup_page_element', 'add_readonly_display', 'varchar', NULL, NULL, NULL, NULL, '1', NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e5fdc3-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100419', '修改只读', 'PN100411', '110', NULL, NULL, NULL, 'view_setup_page_element', 'update_readonly_display', 'varchar', NULL, NULL, NULL, NULL, '1', NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e5ff25-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100420', '按钮快捷键', 'PN100411', '120', NULL, NULL, NULL, 'view_setup_page_element', 'button_click_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e60082-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100421', '必输', 'PN100411', '130', NULL, NULL, NULL, 'view_setup_page_element', 'not_null_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e601db-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100422', '新增必输', 'PN100411', '140', NULL, NULL, NULL, 'view_setup_page_element', 'add_not_null_display', 'varchar', NULL, NULL, NULL, NULL, '1', NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e60326-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100423', '修改必输', 'PN100411', '150', NULL, NULL, NULL, 'view_setup_page_element', 'update_not_null_display', 'varchar', NULL, NULL, NULL, NULL, '1', NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e6046e-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', '1', '2018-06-04 19:45:46', '1', '0', '0', '1', 'logistics_center', 'PN100424', '隐藏', 'PN100411', '180', NULL, NULL, NULL, 'view_setup_page_element', 'element_hide_display', 'varchar', NULL, NULL, NULL, NULL, '0', NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e605cb-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100425', '按钮位置', 'PN100411', '160', NULL, NULL, NULL, 'view_setup_page_element', 'button_set_display', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e6071a-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100426', '按钮图片', 'PN100411', '170', NULL, NULL, NULL, 'view_setup_page_element', 'button_picture', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e60f45-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', '1', '2018-06-04 19:45:46', '1', '0', '0', '1', 'logistics_center', 'PN100427', '元素说明', 'PN100411', '180', NULL, NULL, NULL, 'view_setup_page_element', 'element_title', 'varchar', NULL, NULL, NULL, NULL, '0', NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e610ca-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', '1', '2018-06-04 19:45:46', '1', '0', '0', '1', 'logistics_center', 'PN100428', '字符过滤', 'PN100411', '190', NULL, NULL, NULL, 'view_setup_page_element', 'is_filt_display', 'varchar', NULL, '1', NULL, NULL, '0', NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e6122d-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100429', '元素ID', 'PN100411', '10', NULL, NULL, NULL, 'view_setup_page_element', 'element_id', 'varchar', 'asc', '1', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e61388-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100430', '元素名称', 'PN100411', '20', NULL, NULL, NULL, 'view_setup_page_element', 'element_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d1e614db-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:46', 'ceshi', '2018-06-04 19:45:46', 'ceshi', '0', '0', '1', 'ceshi', 'PN100431', '显示顺序', 'PN100411', '30', NULL, NULL, NULL, 'view_setup_page_element', 'element_seq', 'int', NULL, NULL, NULL, NULL, NULL, NULL);

-- 页面布局 - 查询列表
DELETE FROM
	setup_page_table
WHERE
	layout_id = 'PN100432';

INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d25e86e4-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:47', 'ceshi', '2018-06-04 19:45:47', 'ceshi', '0', '0', '1', 'ceshi', 'PN100433', '显示格式', 'PN100432', '50', NULL, NULL, NULL, 'view_setup_page_table', 'column_format', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d25e8ba7-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:47', '1', '2018-06-04 19:45:47', '1', '0', '0', '1', 'logistics_center', 'PN100434', '隐藏', 'PN100432', '60', NULL, NULL, NULL, 'view_setup_page_table', 'column_hide_display', 'varchar', NULL, NULL, NULL, NULL, '0', NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d25e8d72-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:47', 'ceshi', '2018-06-04 19:45:47', 'ceshi', '0', '0', '1', 'ceshi', 'PN100435', '列ID', 'PN100432', '10', NULL, NULL, NULL, 'view_setup_page_table', 'column_id', 'varchar', 'asc', '1', NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d25e8ef4-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:47', 'ceshi', '2018-06-04 19:45:47', 'ceshi', '0', '0', '1', 'ceshi', 'PN100436', '列名', 'PN100432', '20', NULL, NULL, NULL, 'view_setup_page_table', 'column_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d25e9059-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:47', 'ceshi', '2018-06-04 19:45:47', 'ceshi', '0', '0', '1', 'ceshi', 'PN100437', '显示顺序', 'PN100432', '30', NULL, NULL, NULL, 'view_setup_page_table', 'column_seq', 'int', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('d25e91b6-67ec-11e8-9d0d-005056954f8e', '2018-06-04 19:45:47', 'ceshi', '2018-06-04 19:45:47', 'ceshi', '0', '0', '1', 'ceshi', 'PN100438', '宽度', 'PN100432', '40', NULL, NULL, NULL, 'view_setup_page_table', 'column_width', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);

-- 添加复制页面布局

DELETE
FROM
	setup_page
WHERE
	page_id = 'P_YMGX_C';

INSERT INTO `setup_page` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `page_id`, `page_name`, `page_width`, `page_height`, `page_type`, `parent_page_id`, `page_url`) VALUES ('987e6f1c-fbfa-11e7-bd1b-005056952d2b', '2018-01-18 10:52:17', 'admin', '2018-06-05 14:06:02', '1', '0', '0', '3', 'lmis_bcs', 'P_YMGX_C', '复制页面布局', '600', '300', 'page_open', 'P_YMGX', NULL);

DELETE FROM setup_page_layout WHERE layout_id = 'P_YMGX_C_P1';

INSERT INTO `setup_page_layout` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `layout_id`, `page_id`, `layout_name`, `layout_seq`, `layout_width`, `layout_height`, `layout_type`, `parent_layout_id`, `button_flag1`, `button_flag2`, `button_flag3`, `button_flag4`, `button_flag5`, `is_hide`) VALUES ('c8cddd92-fbfa-11e7-bd1b-005056952d2b', '2018-01-18 10:53:38', 'admin', '2018-01-18 10:53:51', 'admin', '0', '0', '1', 'lmis_bcs', 'P_YMGX_C_P1', 'P_YMGX_C', '复制页面布局', '10', '100%', '200px', 'layout_a', NULL, '0', '0', '0', '0', '0', NULL);



-- 复制页面布局添加布局
DELETE
FROM
	setup_page_element
WHERE
	layout_id = 'P_YMGX_C_P1';

INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('150110aa-fbfc-11e7-bd1b-005056952d2b', '2018-01-18 11:02:56', 'admin', '2018-01-18 11:02:56', 'admin', '0', '0', '1', 'lmis_bcs', 'P_YMGX_C_P1_E02', '当前页面ID', 'P_YMGX_C_P1', '10', '100%', NULL, 'h_e_input', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('e7465266-fbfa-11e7-bd1b-005056952d2b', '2018-01-18 10:54:29', 'admin', '2018-01-18 11:03:04', 'admin', '0', '0', '3', 'lmis_bcs', 'P_YMGX_C_P1_E01', '来源页面ID', 'P_YMGX_C_P1', '20', '100%', NULL, 'h_e_select', NULL, NULL, 'page_list', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('4ef16ec5-fbfb-11e7-bd1b-005056952d2b', '2018-01-18 10:57:23', 'admin', '2018-01-18 10:57:23', 'admin', '0', '0', '1', 'lmis_bcs', 'P_YMGX_C_P1_B01', '确认', 'P_YMGX_C_P1', '110', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);


-- 查询条件-配置查询条件配置-查询条件
DELETE
FROM
	setup_page_element
WHERE
	element_id = 'P_CXTJ_P1_E01';

INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('633f2b21-ead1-11e7-bd1b-005056952d2b', '2017-12-27 14:44:29', 'admin', '2018-06-14 17:18:27', '1', '0', '0', '6', 'lmis_bcs', 'P_CXTJ_P1_E01', '页面ID', 'P_CXTJ_P1', '10', '30%', NULL, 'h_e_select', NULL, NULL, 'page_list', NULL, NULL, 'varchar', NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


-- 查询条件绑定元素
DELETE FROM
setup_page_element
WHERE
	layout_id = 'P_CXTJ_N_P1';


INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('6f5d4a57-ead5-11e7-bd1b-005056952d2b', '2017-12-27 15:13:27', 'admin', '2017-12-27 15:13:27', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_N_P1_B01', '保存', 'P_CXTJ_N_P1', '110', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('8ed48da0-ead5-11e7-bd1b-005056952d2b', '2017-12-27 15:14:20', 'admin', '2017-12-27 15:14:33', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_N_P1_B03', '关闭', 'P_CXTJ_N_P1', '130', NULL, NULL, 'h_e_button', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'bt_centre', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('d53979c1-ead4-11e7-bd1b-005056952d2b', '2017-12-27 15:09:08', 'admin', '2017-12-27 15:09:08', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_N_P1_E05', '对应字段名', 'P_CXTJ_N_P1', '50', '100%', NULL, 'h_e_select', NULL, NULL, NULL, 'setup_page_element', 'column_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('91400bbc-6d53-11e8-9d0d-005056954f8e', '2018-06-11 16:43:52', '1', '2018-06-11 17:19:34', '1', '0', '0', '2', 'logistics_center', 'PN101205', '字段长度', 'P_CXTJ_N_P1', '80', '100%', NULL, 'h_e_input', NULL, '0', NULL, 'setup_page_element', 'column_len', 'int', 'and', '=', NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', NULL, NULL, '0', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('6cb99c9c-000b-11e8-bd1b-005056952d2b', '2018-01-23 15:02:50', 'admin', '2018-01-23 15:30:57', 'admin', '0', '0', '6', 'lmis_bcs', 'P_CXTJ_N_P1_E08', '字段类型', 'P_CXTJ_N_P1', '55', '100%', NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_element', 'column_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('ae657351-6d53-11e8-9d0d-005056954f8e', '2018-06-11 16:44:41', '1', '2018-06-12 09:31:13', '1', '0', '0', '2', 'logistics_center', 'PN101207', '是否隐藏', 'P_CXTJ_N_P1', '100', '100%', NULL, 'h_e_switch', NULL, NULL, NULL, 'setup_page_element', 'element_hide', 'tinyint', 'and', '=', NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', NULL, NULL, '1', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('3f139292-ead4-11e7-bd1b-005056952d2b', '2017-12-27 15:04:57', 'admin', '2017-12-27 15:05:16', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_N_P1_E01', '元素ID', 'P_CXTJ_N_P1', '10', '100%', NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_element', 'element_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('4888ebc1-ead4-11e7-bd1b-005056952d2b', '2017-12-27 15:05:12', 'admin', '2018-01-23 15:32:18', 'admin', '0', '0', '2', 'lmis_bcs', 'P_CXTJ_N_P1_E02', '元素名称', 'P_CXTJ_N_P1', '20', '100%', NULL, 'h_e_input', NULL, '1', NULL, 'setup_page_element', 'element_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('a36a9368-6d53-11e8-9d0d-005056954f8e', '2018-06-11 16:44:22', '1', '2018-06-12 09:31:00', '1', '0', '0', '2', 'logistics_center', 'PN101206', '页面title', 'P_CXTJ_N_P1', '90', '100%', NULL, 'h_e_input', NULL, NULL, NULL, 'setup_page_element', 'element_title', 'varchar', 'and', 'like', NULL, NULL, NULL, NULL, NULL, NULL, 'bt_right', NULL, NULL, '1', NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('6cdbef3c-ead4-11e7-bd1b-005056952d2b', '2017-12-27 15:06:13', 'admin', '2017-12-27 15:06:22', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_N_P1_E03', '元素类型', 'P_CXTJ_N_P1', '30', '100%', NULL, 'h_e_select', NULL, '1', 'html_element', 'setup_page_element', 'element_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('b16764b5-ead4-11e7-bd1b-005056952d2b', '2017-12-27 15:08:08', 'admin', '2017-12-27 15:08:08', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_N_P1_E04', '对应表名', 'P_CXTJ_N_P1', '40', '100%', NULL, 'h_e_select', NULL, NULL, 'lmis_table_name', 'setup_page_element', 'table_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('121669ea-ead5-11e7-bd1b-005056952d2b', '2017-12-27 15:10:51', 'admin', '2017-12-27 15:10:51', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_N_P1_E07', '查询运算符', 'P_CXTJ_N_P1', '70', '100%', NULL, 'h_e_select', NULL, NULL, 'where_operator', 'setup_page_element', 'where_operator', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_element` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `element_id`, `element_name`, `layout_id`, `element_seq`, `element_width`, `element_height`, `element_type`, `default_value`, `element_disabled`, `element_format`, `table_id`, `column_id`, `column_type`, `where_type`, `where_operator`, `add_readonly`, `update_readonly`, `button_click`, `not_null`, `add_not_null`, `update_not_null`, `button_set`, `button_picture`, `element_title`, `element_hide`, `column_len`, `is_filt`) VALUES ('f8d315c9-ead4-11e7-bd1b-005056952d2b', '2017-12-27 15:10:08', 'admin', '2017-12-27 15:10:08', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_N_P1_E06', '查询链接符', 'P_CXTJ_N_P1', '60', '100%', NULL, 'h_e_select', NULL, NULL, 'where_type', 'setup_page_element', 'where_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- 查询条件查询结果配置
DELETE FROM setup_page_table where layout_id = 'P_CXTJ_P2';

INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('f9d0e8a7-ead1-11e7-bd1b-005056952d2b', '2017-12-27 14:48:41', 'admin', '2017-12-27 14:48:41', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_P2_C01', '元素ID', 'P_CXTJ_P2', '10', NULL, NULL, NULL, 'setup_page_element', 'element_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('037098d7-ead2-11e7-bd1b-005056952d2b', '2017-12-27 14:48:57', 'admin', '2017-12-27 14:48:57', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_P2_C02', '元素名称', 'P_CXTJ_P2', '20', NULL, NULL, NULL, 'setup_page_element', 'element_name', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('12918bc2-ead2-11e7-bd1b-005056952d2b', '2017-12-27 14:49:23', 'admin', '2018-06-14 16:45:21', '1', '0', '0', '2', 'lmis_bcs', 'P_CXTJ_P2_C03', '显示顺序', 'P_CXTJ_P2', '30', NULL, NULL, NULL, 'setup_page_element', 'element_seq', 'int', NULL, NULL, 'max', '显示顺序最大值', NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('2abba8bc-ead2-11e7-bd1b-005056952d2b', '2017-12-27 14:50:03', 'admin', '2017-12-27 14:50:03', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_P2_C04', '元素类型', 'P_CXTJ_P2', '40', NULL, NULL, NULL, 'setup_page_element', 'element_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('4afc6fc1-ead2-11e7-bd1b-005056952d2b', '2017-12-27 14:50:58', 'admin', '2017-12-27 14:50:58', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_P2_C05', '对应表名', 'P_CXTJ_P2', '50', NULL, NULL, NULL, 'setup_page_element', 'table_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('549a3819-ead2-11e7-bd1b-005056952d2b', '2017-12-27 14:51:14', 'admin', '2017-12-27 14:51:14', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_P2_C06', '对应字段名', 'P_CXTJ_P2', '60', NULL, NULL, NULL, 'setup_page_element', 'column_id', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('56879fba-000b-11e8-bd1b-005056952d2b', '2018-01-23 15:02:13', 'admin', '2018-01-23 15:06:10', 'admin', '0', '0', '2', 'lmis_bcs', 'P_CXTJ_P2_C09', '字段类型', 'P_CXTJ_P2', '65', NULL, NULL, NULL, 'setup_page_element', 'column_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('657e8fa6-ead2-11e7-bd1b-005056952d2b', '2017-12-27 14:51:42', 'admin', '2017-12-27 14:51:42', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_P2_C07', '查询链接符', 'P_CXTJ_P2', '70', NULL, NULL, NULL, 'setup_page_element', 'where_type', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('72be1681-ead2-11e7-bd1b-005056952d2b', '2017-12-27 14:52:04', 'admin', '2017-12-27 14:52:04', 'admin', '0', '0', '1', 'lmis_bcs', 'P_CXTJ_P2_C08', '查询运算符', 'P_CXTJ_P2', '80', NULL, NULL, NULL, 'setup_page_element', 'where_operator', 'varchar', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `setup_page_table` (`id`, `create_time`, `create_by`, `update_time`, `update_by`, `is_deleted`, `is_disabled`, `version`, `pwr_org`, `column_id`, `column_name`, `layout_id`, `column_seq`, `column_width`, `column_height`, `column_format`, `table_id`, `table_column_id`, `column_type`, `orderby_type`, `orderby_seq`, `count_type`, `count_name`, `column_hide`, `list_type`) VALUES ('f482b0a3-6de0-11e8-9d0d-005056954f8e', '2018-06-12 09:35:57', '1', '2018-06-12 09:36:21', '1', '0', '0', '2', 'logistics_center', 'PN101210', '字段长度', 'P_CXTJ_P2', '100', NULL, NULL, NULL, 'setup_page_element', 'column_len', 'int', NULL, NULL, NULL, NULL, '0', 'lt_plain');
