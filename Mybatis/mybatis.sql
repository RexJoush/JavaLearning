/*
    user 表，用于 Demo01 - Demo07
 */

/*
 Navicat Premium Data Transfer

 Source Server         : Joush
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : com.joush

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_as_cs NULL DEFAULT NULL,
     `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_as_cs NULL DEFAULT NULL,
     `sex` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_as_cs NULL DEFAULT NULL,
     `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_as_cs NULL DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_cs_0900_as_cs ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


/*
    account 表，用于 Demo06
*/
/*
 Navicat Premium Data Transfer

 Source Server         : Joush
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : com.joush

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `uid` int(11) NOT NULL COMMENT '用户编号',
    `money` double(10, 2) NOT NULL COMMENT '金额',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `FK_Reference_8`(`uid`) USING BTREE,
    CONSTRAINT `FK_Reference_8` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_cs_0900_as_cs ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


/*
    role 角色表，用于 Demo07
 */
/*
Navicat Premium Data Transfer

Source Server         : Joush
Source Server Type    : MySQL
Source Server Version : 80011
Source Host           : localhost:3306
Source Schema         : com.joush

Target Server Type    : MySQL
Target Server Version : 80011
File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
     `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_as_cs NULL DEFAULT NULL COMMENT '角色名称',
     `role_desc` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_cs_0900_as_cs NULL DEFAULT NULL COMMENT '角色描述',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_cs_0900_as_cs ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

/*
   user_role 表，用于 Demo07
 */
/*
Navicat Premium Data Transfer

Source Server         : Joush
Source Server Type    : MySQL
Source Server Version : 80011
Source Host           : localhost:3306
Source Schema         : com.joush

Target Server Type    : MySQL
Target Server Version : 80011
File Encoding         : 65001

*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
      `uid` int(11) NOT NULL COMMENT '用户编号',
      `rid` int(11) NOT NULL COMMENT '角色编号',
      PRIMARY KEY (`uid`, `rid`) USING BTREE,
      INDEX `FK_Reference_9`(`rid`) USING BTREE,
      CONSTRAINT `FK_Reference_10` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
      CONSTRAINT `FK_Reference_9` FOREIGN KEY (`rid`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_cs_0900_as_cs ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;