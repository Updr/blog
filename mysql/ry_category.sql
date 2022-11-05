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

 Date: 28/10/2022 14:32:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ry_category
-- ----------------------------
DROP TABLE IF EXISTS `ry_category`;
CREATE TABLE `ry_category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名',
  `pid` bigint(0) NULL DEFAULT -1 COMMENT '父分类id，如果没有父分类为-1',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '状态：1正常，0禁用',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ry_category
-- ----------------------------
INSERT INTO `ry_category` VALUES (1, 'java', -1, 'java相关内容', '1', NULL, '2022-10-04 20:59:30', NULL, NULL, 0);
INSERT INTO `ry_category` VALUES (2, 'vue', -1, 'vue相关内容', '1', NULL, '2022-10-04 21:00:12', NULL, NULL, 0);
INSERT INTO `ry_category` VALUES (3, 'C++', -1, 'C++相关', '1', 1, '2022-10-27 10:43:19', 1, '2022-10-27 12:01:22', 1);

SET FOREIGN_KEY_CHECKS = 1;
