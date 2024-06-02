/*
Navicat MySQL Data Transfer

Source Server         : yanxiu
Source Server Version : 80033
Source Host           : localhost:3306
Source Database       : oasystem

Target Server Type    : MYSQL
Target Server Version : 80033
File Encoding         : 65001

Date: 2024-06-02 15:31:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_admin_user`;
CREATE TABLE `tb_admin_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'シーケンスid',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT 'ユーザー名',
  `password_md5` varchar(50) NOT NULL DEFAULT '' COMMENT 'パスワード',
  `user_token` varchar(50) NOT NULL DEFAULT '' COMMENT 'トークン',
  `is_skj` tinyint NOT NULL DEFAULT '0' COMMENT '削除フラグ　0:未削除　1:削除済',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of tb_admin_user
-- ----------------------------
INSERT INTO `tb_admin_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '9a7b027587d5794c23bcb22b27acb759', '0', '2023-01-01 11:50:13');
INSERT INTO `tb_admin_user` VALUES ('2', 'test', 'e10adc3949ba59abbe56e057f20f883e', '', '0', '2024-05-15 21:49:54');

-- ----------------------------
-- Table structure for `tb_ssm_article`
-- ----------------------------
DROP TABLE IF EXISTS `tb_ssm_article`;
CREATE TABLE `tb_ssm_article` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'シーケンスid',
  `article_title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'タイトル',
  `article_content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'コンテンツ',
  `add_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT '作成者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成時間',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_skj` tinyint NOT NULL DEFAULT '0' COMMENT '削除フラグ　0:未削除　1:削除済',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of tb_ssm_article
-- ----------------------------
INSERT INTO `tb_ssm_article` VALUES ('1', '321', 0x3C703E3132333C2F703E, '321', '2024-05-22 21:28:56', '2024-05-22 21:28:56', '1');