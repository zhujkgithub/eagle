/*
Navicat MySQL Data Transfer

Source Server         :
Source Server Version : 50722
Source Host           :
Source Database       : eagle

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-04-06 17:25:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(32) NOT NULL COMMENT '唯一索引Id',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `filename` varchar(255) DEFAULT NULL COMMENT '文件名',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `path` varchar(255) DEFAULT NULL COMMENT '文件存储路径',
  `size` bigint(32) DEFAULT NULL COMMENT '文件大小',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES ('561895955088015363', 'demo-1-bg.jpg', '1554008035212.jpg', 'other', '\\other\\1554008035212.jpg', '174899', '2019-03-31 12:53:55');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(32) NOT NULL COMMENT '编号',
  `pid` bigint(32) NOT NULL COMMENT '父级编号',
  `pids` varchar(1023) NOT NULL COMMENT '所有父级编号',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `sort` bigint(15) NOT NULL COMMENT '排序',
  `href` varchar(255) DEFAULT NULL COMMENT '链接',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `disabled` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否在菜单中显示：0显示 1隐藏',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除0不限制 1删除',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1:菜单 2权限',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`pid`) USING BTREE,
  KEY `sys_menu_del_flag` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '-1', '-1', '用户管理', '1', '', null, null, null, null, null, '2019-04-02 20:42:06', null, '0', '0', '1');
INSERT INTO `sys_menu` VALUES ('2', '1', '1', '技术部', '1', '/user/index', null, '', null, null, null, '2019-04-02 20:42:49', null, '0', '0', '1');
INSERT INTO `sys_menu` VALUES ('3', '2', '2', '查看', '1', '', null, 'user:list', null, null, null, '2019-04-02 20:43:28', null, '0', '0', '2');
INSERT INTO `sys_menu` VALUES ('4', '2', '2', '增加', '2', '', null, 'user:add', null, null, null, '2019-04-02 20:49:30', null, '0', '0', '2');
INSERT INTO `sys_menu` VALUES ('5', '1', '1', '测试部', '2', '/user/test/index', null, null, null, null, null, '2019-04-02 20:49:33', null, '0', '0', '1');
INSERT INTO `sys_menu` VALUES ('6', '-1', '-1', '系统管理', '2', null, null, null, null, null, null, '2019-04-02 20:49:48', null, '0', '0', '1');
INSERT INTO `sys_menu` VALUES ('7', '6', '6', '角色管理', '1', '/sys/role/index', null, null, null, null, null, '2019-04-02 20:49:52', null, '0', '0', '1');
INSERT INTO `sys_menu` VALUES ('8', '6', '6', '菜单管理', '2', '/sys/menu/index', null, null, null, null, null, '2019-04-02 20:49:54', null, '0', '0', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `remarks` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `disabled` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否隐藏0显示1隐藏',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除0不，1删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', null, null, null, null, null, '0', '0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(32) NOT NULL COMMENT 'ID',
  `role_id` bigint(32) NOT NULL COMMENT '菜单ID',
  `menu_id` bigint(32) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用会员角色菜单表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('3', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('4', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('5', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('6', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('7', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('8', '1', '8');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(32) NOT NULL COMMENT 'id',
  `username` varchar(32) NOT NULL COMMENT '登录名',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称',
  `realname` varchar(64) DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(32) DEFAULT NULL COMMENT '证件号码',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `age` tinyint(4) DEFAULT NULL COMMENT '年龄',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机',
  `telephone` varchar(15) DEFAULT NULL COMMENT '电话',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `last_login_ip` varchar(32) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_count` bigint(15) DEFAULT '0' COMMENT '登录次数',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否锁定，0：无限制，1：锁定',
  `disabled` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否展示 0：显示 1：隐藏',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除：0：可用，1：删除',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态：0审核通过  1禁用  2待审核',
  `sort` tinyint(4) NOT NULL DEFAULT '0' COMMENT '排序',
  `rank` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户级别：只能管理比自己级别小的',
  `error_time` datetime DEFAULT NULL COMMENT '登录错误时间',
  `error_ip` varchar(32) DEFAULT NULL COMMENT '登录错误ip',
  `error_count` bigint(15) DEFAULT '0' COMMENT '登录错误次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, null, null, null, null, null, null, null, null, '88', null, null, null, null, '2019-03-22 22:29:33', '0', '0', '0', '0', '0', '0', null, null, '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NOT NULL COMMENT '用户编号',
  `role_id` bigint(32) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(32) NOT NULL COMMENT '主键Id',
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码：加密',
  `realname` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像',
  `id_card` varchar(32) DEFAULT NULL COMMENT '证件号码',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `age` tinyint(4) DEFAULT NULL COMMENT '年龄',
  `mobile` varchar(15) DEFAULT NULL COMMENT '移动电话',
  `phone` varchar(15) DEFAULT NULL COMMENT '手机号码',
  `create_user` bigint(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否锁定，0：无限制，1：锁定',
  `disabled` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否隐藏0否，1隐藏',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除0否，1是',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户类型',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态0审核通过，1禁用，2待审核',
  `last_login_ip` varchar(32) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `login_count` bigint(15) NOT NULL DEFAULT '0' COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '18339990230', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '1', null, null, null, null, null, null, null, null, '0', '0', '0', '0', '0', '0:0:0:0:0:0:0:1', '2019-04-06 15:14:19', '5');
INSERT INTO `user` VALUES ('2', '15639066330', 'e10adc3949ba59abbe56e057f20f883e', null, null, null, null, '2', null, null, null, null, null, null, null, null, '0', '0', '0', '0', '0', null, '2019-04-05 19:35:09', '0');
