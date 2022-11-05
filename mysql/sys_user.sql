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

 Date: 28/10/2022 14:33:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '账号状态（1正常 0停用）',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'test', '测试', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', '1', '1', '1552991515@qq.com', '18832938337', '1', 'http://rjmbbrdpo.hb-bkt.clouddn.com/2022/10/13/f810fa8dbc92465d924f52b3d3b6c824.jpg', NULL, '2022-10-06 19:08:59', NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (2, 'mytest', '测试用户1', '$2a$10$DEbs7EzVFTNatMlHLJtqfOdGF9lRMR7/ZHPLTBJ.U201PTWbJ6AXm', '1', '1', '188@qq.com', NULL, '0', 'http://rjmbbrdpo.hb-bkt.clouddn.com/2022/10/13/6aaca2bf6fef42eaaf5936a6afa7d246.jpg', NULL, '2022-10-07 22:25:10', 1, '2022-10-23 17:40:51', 0);
INSERT INTO `sys_user` VALUES (3, 'test1', 'test1', '$2a$10$/tin/T7wCm/kmvi/b0xU9uTRVp0FMIf10EFvOp0bYpAe3v.Mvr3fK', '0', '1', '11@qq.com', NULL, NULL, NULL, -1, '2022-10-17 13:31:06', 1, '2022-10-26 22:39:04', 1);
INSERT INTO `sys_user` VALUES (4, '我是测试用户', '测试用户1', '$2a$10$dTguJob9TFg/bqT.IEYK7ebPvh3XgFLT2UFi/3e2sOT3XNHFauE7O', '0', '1', '144@qq.com', '13932934235', '0', NULL, 1, '2022-10-26 11:11:29', 1, '2022-10-26 23:03:46', 0);

SET FOREIGN_KEY_CHECKS = 1;
