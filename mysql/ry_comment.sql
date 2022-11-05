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

 Date: 28/10/2022 14:32:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ry_comment
-- ----------------------------
DROP TABLE IF EXISTS `ry_comment`;
CREATE TABLE `ry_comment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
  `article_id` bigint(0) NULL DEFAULT NULL COMMENT '文章id',
  `root_id` bigint(0) NULL DEFAULT -1 COMMENT '根评论id',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论内容',
  `to_comment_user_id` bigint(0) NULL DEFAULT -1 COMMENT '所回复的目标评论的userid',
  `to_comment_id` bigint(0) NULL DEFAULT -1 COMMENT '回复目标评论id',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ry_comment
-- ----------------------------
INSERT INTO `ry_comment` VALUES (1, '0', 1, -1, '沙发！', -1, -1, 1, '2022-10-09 12:36:22', NULL, NULL, 0);
INSERT INTO `ry_comment` VALUES (2, '0', 1, -1, '扣1送地狱火', -1, -1, 1, '2022-10-09 19:58:25', NULL, NULL, 0);
INSERT INTO `ry_comment` VALUES (3, '0', 1, 1, '蹭个前排', 1, 1, 1, '2022-10-09 19:59:49', NULL, NULL, 0);
INSERT INTO `ry_comment` VALUES (7, '0', 1, -1, '测试', -1, -1, 1, '2022-10-10 17:30:01', 1, '2022-10-10 17:30:01', 0);
INSERT INTO `ry_comment` VALUES (8, '0', 1, 7, '测试回复', 1, 7, 1, '2022-10-10 17:30:16', 1, '2022-10-10 17:30:16', 0);
INSERT INTO `ry_comment` VALUES (9, '0', 1, -1, '看看还有bug吗[挤眼]', -1, -1, 1, '2022-10-10 17:43:29', 1, '2022-10-10 17:43:29', 0);
INSERT INTO `ry_comment` VALUES (10, '1', 1, -1, '测试友链评论', -1, -1, 1, '2022-10-11 16:13:43', 1, '2022-10-11 16:13:43', 0);
INSERT INTO `ry_comment` VALUES (11, '1', 1, 10, '看看能不能回复', 1, 10, 1, '2022-10-11 16:16:26', 1, '2022-10-11 16:16:26', 0);

SET FOREIGN_KEY_CHECKS = 1;
