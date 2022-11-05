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

 Date: 28/10/2022 14:32:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ry_article
-- ----------------------------
DROP TABLE IF EXISTS `ry_article`;
CREATE TABLE `ry_article`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '文章内容',
  `summary` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `category_id` bigint(0) NULL DEFAULT NULL COMMENT '所属分类id',
  `thumbnail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '缩略图',
  `is_top` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否置顶（0否，1是）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '发布状态（0草稿，1已发布）',
  `view_count` bigint(0) NULL DEFAULT 0 COMMENT '访问量',
  `is_comment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否允许评论（0否，1是）',
  `create_by` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(0) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ry_article
-- ----------------------------
INSERT INTO `ry_article` VALUES (1, '这是一个标题', '这是文章内容', '这是文章摘要', 1, 'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/fd2e9460c58a4af3bbeae5d9ed581688.png', '0', '1', 6, '1', 1, '2022-10-03 19:45:05', NULL, NULL, 0);
INSERT INTO `ry_article` VALUES (2, '我是标题', '我是文章内容', '我是文章摘要', 2, 'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/fd2e9460c58a4af3bbeae5d9ed581688.png', '0', '1', 5, '1', 1, '2022-10-04 10:33:55', NULL, NULL, 0);
INSERT INTO `ry_article` VALUES (3, '这是置顶标题', '这是置顶内容', '这是置顶摘要', 1, 'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/15/fd2e9460c58a4af3bbeae5d9ed581688.png', '0', '1', 45, '1', 1, '2022-10-05 19:03:05', NULL, NULL, 0);
INSERT INTO `ry_article` VALUES (4, '测试能不能写博客', '# 一级标题\n## 二级标题\n_粗体_\n![pp.jpg](可能发生了错误！)', '测试新增博客', 1, '可能发生了错误！', '0', '1', 0, '1', -1, '2022-10-21 11:05:55', 1, '2022-10-21 19:01:28', 1);
INSERT INTO `ry_article` VALUES (5, '1', '# 1\n_num_\n![pp.jpg](http://rjmbbrdpo.hb-bkt.clouddn.com/2022/10/21/1809c0840535497d99252a45dfcc024c.jpg)', '2', 1, 'http://rjmbbrdpo.hb-bkt.clouddn.com/2022/10/21/b94cbf6031ea4ad9be4a3a536741678c.jpg', '1', '1', 8, '1', 1, '2022-10-21 12:06:46', 1, '2022-10-21 18:46:39', 0);

SET FOREIGN_KEY_CHECKS = 1;
