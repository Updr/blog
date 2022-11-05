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

 Date: 28/10/2022 14:32:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ry_link
-- ----------------------------
DROP TABLE IF EXISTS `ry_link`;
CREATE TABLE `ry_link`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '友链网站名称',
  `logo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '友链网站logo',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '友链网站描述',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '友链网站地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ry_link
-- ----------------------------
INSERT INTO `ry_link` VALUES (1, '百度', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', '百度搜索', 'https://www.baidu.com', '0', NULL, '2022-10-05 20:46:42', NULL, NULL, 0);
INSERT INTO `ry_link` VALUES (2, 'bilibili', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', '哔哩哔哩弹幕网站', 'https://www.bilibili.com/', '1', NULL, '2022-10-27 16:00:26', 1, '2022-10-27 16:50:09', 1);
INSERT INTO `ry_link` VALUES (3, '哔哩哔哩', 'http://rjmbbrdpo.hb-bkt.clouddn.com/bilibililogo.jpg', 'bilibili弹幕网站', 'https://www.bilibili.com/', '0', 1, '2022-10-27 16:42:09', 1, '2022-10-27 16:59:37', 0);

SET FOREIGN_KEY_CHECKS = 1;
