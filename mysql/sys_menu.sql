/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : ry_blog

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 28/10/2022 14:32:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(0) NULL DEFAULT 0 COMMENT '是否为外链（1是 0否）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '菜单状态（1显示 0隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '菜单状态（1正常 0停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2031 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 0, 'M', '1', '1', '', 'system', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '系统管理目录', '0');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 0, 'C', '1', '1', 'system:user:list', 'user', 0, '2022-10-18 14:20:30', 1, '2022-10-18 14:20:30', '用户管理菜单', '0');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 0, 'C', '1', '1', 'system:role:list', 'peoples', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '角色管理菜单', '0');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 0, 'C', '1', '1', 'system:menu:list', 'tree-table', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '菜单管理菜单', '0');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 0, 'F', '1', '1', 'system:user:query', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 0, 'F', '1', '1', 'system:user:add', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 0, 'F', '1', '1', 'system:user:edit', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 0, 'F', '1', '1', 'system:user:remove', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 0, 'F', '1', '1', 'system:user:export', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 0, 'F', '1', '1', 'system:user:import', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 0, 'F', '1', '1', 'system:user:resetPwd', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 0, 'F', '1', '1', 'system:role:query', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 0, 'F', '1', '1', 'system:role:add', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 0, 'F', '1', '1', 'system:role:edit', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 0, 'F', '1', '1', 'system:role:remove', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 0, 'F', '1', '1', 'system:role:export', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 0, 'F', '1', '1', 'system:menu:query', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 0, 'F', '1', '1', 'system:menu:add', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 0, 'F', '1', '1', 'system:menu:edit', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 0, 'F', '1', '1', 'system:menu:remove', '#', 0, '2022-10-18 14:20:30', 0, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (2017, '内容管理', 0, 4, 'content', NULL, 0, 'M', '1', '1', NULL, 'dict', NULL, '2022-10-18 14:20:30', 1, '2022-10-22 20:33:04', '', '0');
INSERT INTO `sys_menu` VALUES (2018, '分类管理', 2017, 1, 'category', 'content/category/index', 0, 'C', '1', '1', 'content:category:list', 'example', NULL, '2022-10-18 14:20:30', NULL, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (2019, '文章管理', 2017, 0, 'article', 'content/article/index', 0, 'C', '1', '1', 'content:article:list', 'build', NULL, '2022-10-18 14:20:30', NULL, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (2021, '标签管理', 2017, 6, 'tag', 'content/tag/index', 0, 'C', '1', '1', 'content:tag:index', 'component', NULL, '2022-10-18 14:20:30', 1, '2022-10-22 20:34:16', '', '0');
INSERT INTO `sys_menu` VALUES (2022, '友链管理', 2017, 4, 'link', 'content/link/index', 0, 'C', '1', '1', 'content:link:list', 'people', NULL, '2022-10-18 14:20:30', 1, '2022-10-22 20:30:00', '', '0');
INSERT INTO `sys_menu` VALUES (2023, '写博文', 0, 0, 'write', 'content/article/write/index', 0, 'C', '1', '1', 'content:article:writer', 'edit', NULL, '2022-10-18 14:20:30', 1, '2022-10-22 20:31:32', '', '0');
INSERT INTO `sys_menu` VALUES (2024, '友链新增', 2022, 0, '', NULL, 0, 'F', '1', '1', 'content:link:add', '#', NULL, '2022-10-18 14:20:30', NULL, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (2025, '友链修改', 2022, 1, '', NULL, 0, 'F', '1', '1', 'content:link:edit', '#', NULL, '2022-10-18 14:20:30', NULL, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (2026, '友链删除', 2022, 1, '', NULL, 0, 'F', '1', '1', 'content:link:remove', '#', NULL, '2022-10-18 14:20:30', NULL, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (2027, '友链查询', 2022, 2, '', NULL, 0, 'F', '1', '1', 'content:link:query', '#', NULL, '2022-10-18 14:20:30', NULL, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (2028, '导出分类', 2018, 1, '', NULL, 0, 'F', '1', '1', 'content:category:export', '#', NULL, '2022-10-18 14:20:30', NULL, '2022-10-18 14:20:30', '', '0');
INSERT INTO `sys_menu` VALUES (2029, '404', 2023, 1, '/404', NULL, 0, 'M', '1', '1', NULL, '404', 1, '2022-10-22 19:05:57', 1, '2022-10-22 20:28:35', '', '1');
INSERT INTO `sys_menu` VALUES (2030, 'bug11', 2029, 1, 'bug', '404/bug', 0, 'C', '1', '1', 'bug', 'bug', 1, '2022-10-22 19:51:24', 1, '2022-10-22 20:26:41', '', '1');

SET FOREIGN_KEY_CHECKS = 1;
