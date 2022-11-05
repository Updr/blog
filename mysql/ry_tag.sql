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

 Date: 28/10/2022 14:32:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ry_tag
-- ----------------------------
DROP TABLE IF EXISTS `ry_tag`;
CREATE TABLE `ry_tag`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ry_tag
-- ----------------------------
INSERT INTO `ry_tag` VALUES (1, 'Java', 1, '2022-10-17 10:43:08', 1, '2022-10-20 20:01:12', 0, 'Java标签');
INSERT INTO `ry_tag` VALUES (2, 'Vue', 1, '2022-10-20 13:16:10', 1, '2022-10-20 20:01:29', 0, 'Vue标签');
INSERT INTO `ry_tag` VALUES (3, 'C++', 1, '2022-10-20 15:25:41', 1, '2022-10-20 20:01:29', 0, 'C++标签');
INSERT INTO `ry_tag` VALUES (4, 'Python', 1, '2022-10-20 16:30:38', 1, '2022-10-20 16:30:38', 1, 'Python标签');
INSERT INTO `ry_tag` VALUES (5, '测试1', 1, '2022-10-22 20:09:31', 1, '2022-10-22 20:15:38', 1, '测试1');
INSERT INTO `ry_tag` VALUES (6, '测试2', 1, '2022-10-22 20:09:40', 1, '2022-10-22 20:15:38', 1, '测试2');

SET FOREIGN_KEY_CHECKS = 1;
