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

 Date: 28/10/2022 14:32:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ry_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `ry_article_tag`;
CREATE TABLE `ry_article_tag`  (
  `article_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `tag_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '标签id',
  PRIMARY KEY (`article_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ry_article_tag
-- ----------------------------
INSERT INTO `ry_article_tag` VALUES (1, 1);
INSERT INTO `ry_article_tag` VALUES (1, 4);
INSERT INTO `ry_article_tag` VALUES (2, 2);
INSERT INTO `ry_article_tag` VALUES (2, 3);
INSERT INTO `ry_article_tag` VALUES (3, 1);
INSERT INTO `ry_article_tag` VALUES (4, 1);
INSERT INTO `ry_article_tag` VALUES (4, 2);
INSERT INTO `ry_article_tag` VALUES (5, 1);

SET FOREIGN_KEY_CHECKS = 1;
