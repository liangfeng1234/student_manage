/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : stu_management

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 13/02/2023 15:13:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for logon
-- ----------------------------
DROP TABLE IF EXISTS `logon`;
CREATE TABLE `logon`  (
  `userID` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`userID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logon
-- ----------------------------
INSERT INTO `logon` VALUES ('admin', '123456');

-- ----------------------------
-- Table structure for tb_course
-- ----------------------------
DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course`  (
  `courseID` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `courseName` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `semester` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `teacherName` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`courseID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_course
-- ----------------------------
INSERT INTO `tb_course` VALUES ('B08015', 'C++', '2020年冬季学期', '吴洁');
INSERT INTO `tb_course` VALUES ('B08424', 'Python', '2020年冬季学期', '王娟');

-- ----------------------------
-- Table structure for tb_course_selection
-- ----------------------------
DROP TABLE IF EXISTS `tb_course_selection`;
CREATE TABLE `tb_course_selection`  (
  `studentID` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `studentName` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `courseID` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `courseName` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `teacherName` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `semester` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`studentID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_course_selection
-- ----------------------------
INSERT INTO `tb_course_selection` VALUES ('S01001', '陈格', 'B08424', 'Python', '王娟', '2020年冬季学期');
INSERT INTO `tb_course_selection` VALUES ('S01002', '王芳', 'B08015', 'C++', '吴洁', '2020年冬季学期');
INSERT INTO `tb_course_selection` VALUES ('S01003', '张三', 'B08424', 'Python', '王娟', '2020年冬季学期');
INSERT INTO `tb_course_selection` VALUES ('S01004', '李四', 'B08015', 'C++', '吴洁', '2020年冬季学期');
INSERT INTO `tb_course_selection` VALUES ('S01006', '赵六', NULL, NULL, NULL, NULL);
INSERT INTO `tb_course_selection` VALUES ('S01007', '银七', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tb_score
-- ----------------------------
DROP TABLE IF EXISTS `tb_score`;
CREATE TABLE `tb_score`  (
  `courseID` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `courseName` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `studentID` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `studentName` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `score` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`courseID`, `studentID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_score
-- ----------------------------
INSERT INTO `tb_score` VALUES ('B08015', 'C++', 'S01002', '王芳', 85);
INSERT INTO `tb_score` VALUES ('B08015', 'C++', 'S01004', '李四', 95);
INSERT INTO `tb_score` VALUES ('B08424', 'Python', 'S01001', '陈格', 73);
INSERT INTO `tb_score` VALUES ('B08424', 'Python', 'S01003', '张三', 65);

-- ----------------------------
-- Table structure for tb_student
-- ----------------------------
DROP TABLE IF EXISTS `tb_student`;
CREATE TABLE `tb_student`  (
  `studentID` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `studentName` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `studentSex` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `studentBirthday` datetime(6) NULL DEFAULT NULL,
  `clas` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`studentID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_student
-- ----------------------------
INSERT INTO `tb_student` VALUES ('S01001', '陈格', '女', '2000-03-04 00:00:00.000000', '20级自动化1班', '123456');
INSERT INTO `tb_student` VALUES ('S01002', '王芳', '女', '2001-05-04 00:00:00.000000', '20级计算机科学与技术', '123456');
INSERT INTO `tb_student` VALUES ('S01003', '张三', '男', '1999-03-04 00:00:00.000000', '20级自动化2班', '123456');
INSERT INTO `tb_student` VALUES ('S01004', '李四', '男', '1999-11-04 00:00:00.000000', '21级计算机科学与技术', '123456');
INSERT INTO `tb_student` VALUES ('S01006', '赵六', '男', '2001-10-08 00:00:00.000000', '20级土木工程1班', '123456');
INSERT INTO `tb_student` VALUES ('S01007', '银七', '男', '2000-05-24 00:00:00.000000', '20级土木工程2班', '123456');

-- ----------------------------
-- Table structure for tb_teacher
-- ----------------------------
DROP TABLE IF EXISTS `tb_teacher`;
CREATE TABLE `tb_teacher`  (
  `teacherID` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `teacherName` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `teacherSex` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `teacherBirthday` datetime(6) NULL DEFAULT NULL,
  `post` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `department` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`teacherID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_teacher
-- ----------------------------
INSERT INTO `tb_teacher` VALUES ('T01001', '张华', '男', '1985-07-24 00:00:00.000000', '教授', '计算机学院', '123456');
INSERT INTO `tb_teacher` VALUES ('T01002', '吴洁', '女', '1987-06-28 00:00:00.000000', '讲师', '机自学院', '123456');
INSERT INTO `tb_teacher` VALUES ('T01003', '王娟', '女', '1982-12-28 00:00:00.000000', '副教授', '人文学院', '123456');

SET FOREIGN_KEY_CHECKS = 1;
