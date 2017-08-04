/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50537
Source Host           : localhost:3306
Source Database       : recruit

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2014-12-15 23:00:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `attachment`
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `attachmentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `type` varchar(100) NOT NULL COMMENT '关联码表附件类型。',
  `owner` bigint(20) NOT NULL COMMENT '这里关联的所有者的ID',
  `downloadCount` int(11) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`attachmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attachment
-- ----------------------------

-- ----------------------------
-- Table structure for `auditarrange`
-- ----------------------------
DROP TABLE IF EXISTS `auditarrange`;
CREATE TABLE `auditarrange` (
  `auditArrangeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `recruitBatch` bigint(20) NOT NULL,
  `employer` bigint(20) NOT NULL,
  `auditor` bigint(20) NOT NULL,
  `auditType` varchar(100) NOT NULL COMMENT '初审、复审',
  `arrangeTime` datetime NOT NULL,
  PRIMARY KEY (`auditArrangeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auditarrange
-- ----------------------------

-- ----------------------------
-- Table structure for `auditor`
-- ----------------------------
DROP TABLE IF EXISTS `auditor`;
CREATE TABLE `auditor` (
  `auditorID` bigint(20) NOT NULL AUTO_INCREMENT,
  `cardNo` char(18) NOT NULL,
  `name` varchar(20) NOT NULL,
  `gender` char(2) NOT NULL,
  `birthday` date NOT NULL COMMENT '1990年09月19日',
  `education` varchar(100) DEFAULT NULL,
  `graduateSchool` varchar(255) DEFAULT NULL,
  `graduateTime` date DEFAULT NULL,
  `headPic` char(50) DEFAULT NULL,
  `employer` bigint(20) NOT NULL,
  `status` varchar(100) DEFAULT NULL COMMENT '可用，已禁用',
  `remark` varchar(255) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`auditorID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auditor
-- ----------------------------
INSERT INTO `auditor` VALUES ('1', '500235', '张良', '女', '2014-06-07', '本科', '重庆医科大学', '2014-06-07', '', '2', '启用', '他她', '123');
INSERT INTO `auditor` VALUES ('2', '400235111111111111', '涂飞', '男', '2014-06-07', '研究生', '重庆大学', '2014-06-07', '', '2', '启用', '', '123123');
INSERT INTO `auditor` VALUES ('3', '1314520', '王鹏飞', '女', '2014-06-07', '本科', '重庆理工大学', '2014-06-07', '', '2', '启用', '', '123123');

-- ----------------------------
-- Table structure for `candidate`
-- ----------------------------
DROP TABLE IF EXISTS `candidate`;
CREATE TABLE `candidate` (
  `candidateID` bigint(20) NOT NULL AUTO_INCREMENT,
  `cardNo` char(18) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `gender` char(2) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `height` int(4) DEFAULT NULL COMMENT '单位cm',
  `weight` int(4) DEFAULT NULL COMMENT '单位Kg',
  `nation` varchar(100) DEFAULT NULL,
  `census` varchar(30) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `graduateSchool` varchar(30) DEFAULT NULL,
  `graduateTime` date DEFAULT NULL,
  `educationExperience` varchar(100) DEFAULT NULL COMMENT '专科、高中、本科、初中、研究生',
  `degree` varchar(100) DEFAULT NULL COMMENT ' 学士、硕士、博士',
  `profession` varchar(255) DEFAULT NULL,
  `political` varchar(100) DEFAULT NULL COMMENT '中共党员,中共预备党员,共青团员,民革党员,民盟盟员,民建会员,民进会员,农工党党员,致公党党员,九三学社社员,台盟盟员,无党派人士,群众（现称普通公民）,港澳同胞,叛徒,反革命分子',
  `lefteye` char(3) DEFAULT NULL,
  `righteye` char(3) DEFAULT NULL,
  `headPic` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `psw` varchar(30) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL COMMENT '关联码userStatus可用,已禁用',
  PRIMARY KEY (`candidateID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of candidate
-- ----------------------------
INSERT INTO `candidate` VALUES ('3', '50023519890105423X', 'hujun', '男', '2014-06-04', '165', '55', 'nation0004', '重庆云阳', '重庆巴南区红光大道69号', '重庆理工大学', '2014-06-10', 'XL0003', 'XW0002', '软件工程', 'Political0001', '5.3', '4.3', null, null, 'qwe333333', null);

-- ----------------------------
-- Table structure for `codetable`
-- ----------------------------
DROP TABLE IF EXISTS `codetable`;
CREATE TABLE `codetable` (
  `codeTableID` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL COMMENT '规则为：树形编码结构，第一级为：类型编码0001，第二级为，类型编码00010001，后面的依次增加。',
  `name` varchar(100) NOT NULL,
  `codeType` bigint(20) NOT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `hasChild` bit(1) DEFAULT NULL,
  `level` smallint(6) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codeTableID`),
  UNIQUE KEY `CodeTable_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of codetable
-- ----------------------------
INSERT INTO `codetable` VALUES ('4', 'XW0001', '学士', '2', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('5', 'XW0002', '硕士', '2', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('6', 'XW0003', '博士', '2', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('7', 'XL0001', '小学', '3', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('9', 'XL0002', '初中', '3', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('10', 'XL0003', '高中', '3', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('11', 'XL0004', '专科', '3', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('12', 'XL0005', '本科', '3', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('13', 'XL0006', '研究生', '3', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('30', 'Political0001', '中共党员', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('31', 'Political0002', '中共预备党员', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('32', 'Political0003', '共青团员', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('33', 'Political0004', '民革党员', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('34', 'Political0005', '民盟盟员', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('35', 'Political0006', '民建会员', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('36', 'Political0007', '民进会员', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('37', 'Political0008', '普通公民', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('38', 'Political0009', '无党派人士', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('39', 'Political0010', '港澳同胞', '5', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('54', 'noticeType0001', '招录通知', '7', '0', null, '1', '招录通知');
INSERT INTO `codetable` VALUES ('55', 'noticeType0002', '新闻', '7', '0', null, '1', '新闻');
INSERT INTO `codetable` VALUES ('56', 'noticeType0003', '通知公告', '7', '0', null, '1', '通知公告');
INSERT INTO `codetable` VALUES ('57', 'noticeType0004', '政策法规', '7', '0', null, '1', '政策法规');
INSERT INTO `codetable` VALUES ('58', 'noticeType0005', '用人单位通知', '7', '0', null, '1', '用人单位通知');
INSERT INTO `codetable` VALUES ('59', 'noticeType0006', '考生通知', '7', '0', null, '1', '考生通知');
INSERT INTO `codetable` VALUES ('60', 'noticeType0007', '审核人员通知', '7', '0', null, '1', '审核人员通知');
INSERT INTO `codetable` VALUES ('64', 'recruitStatus0001', '新建', '9', '0', null, '1', '下一个手动该表的状态是 发布');
INSERT INTO `codetable` VALUES ('65', 'recruitStatus0002', '已发布', '9', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('66', 'recruitStatus0003', '已发布成绩', '9', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('67', 'recruitStatus0004', '已归档', '9', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('68', 'noticeStatus0001', '新建', '10', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('69', 'noticeStatus0002', '已发布', '10', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('70', 'noticeStatus0003', '已撤回', '10', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('71', 'employerType0001', '事业单位', '11', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('72', 'employerType0002', '行政机关', '11', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('73', 'employerType0003', '教育机构', '11', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('74', 'userStatus0001', '可用', '12', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('75', 'userStatus0002', '已禁用', '12', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('76', 'postStatus0001', '新建', '13', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('77', 'postStatus0002', '已提交', '13', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('78', 'postStatus0003', '审核通过', '13', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('79', 'postStatus0004', '审核未通过', '13', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('80', 'nation0001', '汉族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('81', 'nation0002', '蒙古族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('82', 'nation0003', '满族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('83', 'nation0004', '朝鲜族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('84', 'nation0005', '赫哲族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('85', 'nation0006', '达斡尔族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('86', 'nation0007', '鄂温克族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('87', 'nation0008', '鄂伦春族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('88', 'nation0009', '回族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('89', 'nation0010', '东乡族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('90', 'nation0011', '土族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('91', 'nation0012', '撒拉族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('92', 'nation0013', '保安族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('93', 'nation0014', '裕固族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('94', 'nation0015', '维吾尔族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('95', 'nation0016', '哈萨克族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('96', 'nation0017', '柯尔克孜族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('97', 'nation0018', '土家族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('98', 'nation0019', '高山族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('99', 'nation0020', '其他民族', '14', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('100', 'signup0001', '新建', '15', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('101', 'signup0002', '已提交报名', '15', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('102', 'signup0003', '初审通过', '15', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('103', 'signup0004', '初审未通过', '15', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('104', 'signup0005', '复审通过', '15', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('105', 'signup0006', '复审未通过', '15', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('106', 'signup0007', '已缴费', '15', '0', null, '1', '');
INSERT INTO `codetable` VALUES ('107', 'signup0008', '已打印准考证', '15', '0', null, '1', '');

-- ----------------------------
-- Table structure for `codetype`
-- ----------------------------
DROP TABLE IF EXISTS `codetype`;
CREATE TABLE `codetype` (
  `codeTypeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `code` varchar(100) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codeTypeID`),
  UNIQUE KEY `CodeType_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of codetype
-- ----------------------------
INSERT INTO `codetype` VALUES ('2', '学位', 'XW', '');
INSERT INTO `codetype` VALUES ('3', '学历', 'XL', '');
INSERT INTO `codetype` VALUES ('5', '政治面貌', 'Political', '');
INSERT INTO `codetype` VALUES ('7', '通知类型', 'noticeType', '');
INSERT INTO `codetype` VALUES ('9', '考试状态', 'recruitStatus', '考生的状态有很多个，需要手动该表状态有，发布，发布成绩，归档');
INSERT INTO `codetype` VALUES ('10', '通知状态', 'noticeStatus', '通知、新闻的状态有：新建、已发布、已撤回');
INSERT INTO `codetype` VALUES ('11', '单位类型', 'employerType', '事业单位、行政机关、教育机构等');
INSERT INTO `codetype` VALUES ('12', '用户状态', 'userStatus', '用于表示用户的状态：可用，已禁用等');
INSERT INTO `codetype` VALUES ('13', '岗位状态', 'postStatus', '岗位的状态：新建、提交、审核通过、审核未通过');
INSERT INTO `codetype` VALUES ('14', '民族', 'nation', '这里保存了所有的民族');
INSERT INTO `codetype` VALUES ('15', '考生报名状态', 'signup', '考生的报名的各个状态');
INSERT INTO `codetype` VALUES ('16', 'ddd', 'ddd', 'ddd');
INSERT INTO `codetype` VALUES ('17', 'ddd', 'dddd', 'ddd');
INSERT INTO `codetype` VALUES ('18', 'aaa', 'aaaa', '');
INSERT INTO `codetype` VALUES ('19', 'ccc', 'ccc', 'ccc');

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `departmentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `type` varchar(100) NOT NULL COMMENT '单位，部门',
  `parent` bigint(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `hasChild` bit(1) DEFAULT NULL,
  PRIMARY KEY (`departmentID`),
  UNIQUE KEY `Department_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('8', '技术部', '单位', '0', '13372662542', '得到', '0001', '');
INSERT INTO `department` VALUES ('9', '产品技术部', '部门', '0', '18717640134', '任务而', '0002', '');

-- ----------------------------
-- Table structure for `employer`
-- ----------------------------
DROP TABLE IF EXISTS `employer`;
CREATE TABLE `employer` (
  `employerID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `orgCode` varchar(255) NOT NULL,
  `type` varchar(100) NOT NULL COMMENT '事业单位、行政机关',
  `contact` varchar(20) NOT NULL,
  `contactPhone` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  `orgAddress` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `account` varchar(30) NOT NULL,
  `status` varchar(100) NOT NULL COMMENT '关联码表”userStatus”可用,已禁用',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`employerID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employer
-- ----------------------------
INSERT INTO `employer` VALUES ('1', '重庆理工大学', 'cqut', 'employerType0003', '轻轻的', '123123123', '123', '红光大道69号', 'www.cqut.edu.cn', 'hujunil@qq.com', 'cqut', 'userStatus0001', 'aksdjfklsjdf');
INSERT INTO `employer` VALUES ('2', '奇虎360科技有限公司', '010205', 'employerType0001', '周鸿祎', '55785584', '123', '北京', '360.cn', '234234@360.cn', 'qihoo', 'userStatus0001', '阿道夫斯蒂芬');

-- ----------------------------
-- Table structure for `examroom`
-- ----------------------------
DROP TABLE IF EXISTS `examroom`;
CREATE TABLE `examroom` (
  `examRoomID` bigint(20) NOT NULL AUTO_INCREMENT,
  `recruitSignup` bigint(20) NOT NULL COMMENT '报名号',
  `recruitBatch` bigint(20) NOT NULL,
  `examSubject` bigint(20) NOT NULL COMMENT '考试科目',
  `recruitPlace` varchar(255) NOT NULL COMMENT '考场名称',
  `recruitAddress` varchar(255) NOT NULL COMMENT '考场地址',
  `placeNum` varchar(20) NOT NULL COMMENT '考场号',
  `seatNum` varchar(20) NOT NULL COMMENT '座位号',
  `recruitSTime` datetime DEFAULT NULL COMMENT '考试考试时间精确到分',
  `recruitETime` datetime DEFAULT NULL COMMENT '考试结束时间精确到分',
  PRIMARY KEY (`examRoomID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of examroom
-- ----------------------------
INSERT INTO `examroom` VALUES ('9', '1', '14', '5', '重庆理工大学', '红光大道69号', '301', '1', '2014-06-27 04:05:46', '2014-06-28 04:05:46');
INSERT INTO `examroom` VALUES ('10', '1', '14', '6', '重庆理工大学', '红光大道70号', '302', '2', '2014-06-27 04:05:46', '2014-06-28 04:05:46');
INSERT INTO `examroom` VALUES ('11', '1', '14', '7', '重庆理工大学', '红光大道71号', '303', '3', '2014-06-27 04:05:46', '2014-06-28 04:05:46');

-- ----------------------------
-- Table structure for `examscore`
-- ----------------------------
DROP TABLE IF EXISTS `examscore`;
CREATE TABLE `examscore` (
  `examScoreID` bigint(20) NOT NULL AUTO_INCREMENT,
  `recruitSignup` bigint(20) NOT NULL,
  `recruitBatch` bigint(20) NOT NULL,
  `examSubject` bigint(20) NOT NULL,
  `score` double DEFAULT NULL,
  PRIMARY KEY (`examScoreID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of examscore
-- ----------------------------
INSERT INTO `examscore` VALUES ('1', '1', '14', '5', '95.5');

-- ----------------------------
-- Table structure for `examsubject`
-- ----------------------------
DROP TABLE IF EXISTS `examsubject`;
CREATE TABLE `examsubject` (
  `examSubjectID` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '岗位等级代号0001',
  `name` varchar(100) DEFAULT NULL,
  `postLevel` bigint(20) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`examSubjectID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of examsubject
-- ----------------------------
INSERT INTO `examsubject` VALUES ('5', 'GWYZK00010001', '行政职业能力测验', '3', '数量关系、常识判断 等');
INSERT INTO `examsubject` VALUES ('6', 'GWYZK00010002', '申论', '3', '阅读理解、综合分析 等');
INSERT INTO `examsubject` VALUES ('7', 'GWYZK00010003', '公共基础知识', '3', '法律、政治、经济 等');

-- ----------------------------
-- Table structure for `log`
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `logID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(15) DEFAULT NULL,
  `type` varchar(255) NOT NULL COMMENT '?',
  `name` varchar(30) DEFAULT NULL,
  `content` longtext,
  `data` longtext,
  `operatorID` bigint(20) DEFAULT NULL,
  `operatorCode` varchar(50) DEFAULT NULL,
  `operatorName` varchar(50) DEFAULT NULL,
  `logDate` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`logID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for `module`
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `moduleID` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL COMMENT '规则为：树形编码结构，第一级为：0001，第二级为，00010001',
  `name` varchar(100) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent` bigint(20) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `hasChild` bit(1) DEFAULT NULL,
  PRIMARY KEY (`moduleID`),
  UNIQUE KEY `Module_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('1', '0001', '所有模块', null, '0', '123', '');
INSERT INTO `module` VALUES ('2', '00010001', '系统设置', '', '1', '系统一级菜单', '');
INSERT INTO `module` VALUES ('3', '00010002', '人事局子系统', '', '1', '系统一级菜单', '');
INSERT INTO `module` VALUES ('4', '000100010001', '码表管理', '', '2', '二级菜单', '');
INSERT INTO `module` VALUES ('5', '000100010002', '权限管理', '', '2', '二级菜单', '');
INSERT INTO `module` VALUES ('6', '000100010003', '模块管理', '', '2', '', '');
INSERT INTO `module` VALUES ('7', '000100010004', '系统参数', '', '2', '', '');
INSERT INTO `module` VALUES ('8', '000100010005', '系统日志', '', '2', '', '');
INSERT INTO `module` VALUES ('9', '000100020001', '基础设置', '', '3', '', '');
INSERT INTO `module` VALUES ('10', '000100020002', '招考管理', '', '3', '', '');
INSERT INTO `module` VALUES ('11', '000100020003', '通知新闻', '', '3', '', '');
INSERT INTO `module` VALUES ('12', '000100020004', '考生管理', '', '3', '', '');
INSERT INTO `module` VALUES ('13', '000100020005', '用人单位管理', '', '3', '', '');
INSERT INTO `module` VALUES ('14', '00010003', '考生子系统', '', '1', '', '');
INSERT INTO `module` VALUES ('15', '00010004', '用人单位子系统', '', '1', '', '');
INSERT INTO `module` VALUES ('16', '00010005', '审核人员子系统', '', '1', '', '');
INSERT INTO `module` VALUES ('17', '0001000100010001', '码表/类型管理', 'admin/CodeType/index', '4', '', '');
INSERT INTO `module` VALUES ('19', '0001000100030001', '系统模块管理', 'admin/Module/indexZree', '6', '', '');
INSERT INTO `module` VALUES ('20', '0001000100040001', '系统参数管理', 'admin/SystemParameter/index', '7', '', '');
INSERT INTO `module` VALUES ('21', '0001000100050001', '系统日志管理', 'admin/Log/index', '8', '', '');
INSERT INTO `module` VALUES ('22', '0001000100020001', '单位/部门管理', 'admin/Department/index', '5', '', '');
INSERT INTO `module` VALUES ('23', '0001000100020002', '人事局用户管理', 'admin/Operator/index', '5', '', '');
INSERT INTO `module` VALUES ('24', '0001000100020003', '角色管理', 'admin/Role/index', '5', '', '');
INSERT INTO `module` VALUES ('25', '0001000100020004', '角色模块分配', 'admin/RoleModule/index', '5', '', '');
INSERT INTO `module` VALUES ('26', '0001000100020005', '用户角色分配', 'admin/UserRole/index', '5', '', '');
INSERT INTO `module` VALUES ('27', '0001000100050002', '附件', 'admin/Attachment/index', '8', '', '');
INSERT INTO `module` VALUES ('28', '0001000100020006', '权限点', 'admin/Permission/index', '5', '', '');
INSERT INTO `module` VALUES ('29', '0001000100020007', '角色权限点分配', 'admin/RolePermission/index', '5', '', '');
INSERT INTO `module` VALUES ('30', '0001000200010001', '招考类型管理', 'recruitAdmin/RecruitType/index', '9', '', '');
INSERT INTO `module` VALUES ('33', '0001000200020001', '新增招考批次', 'recruitAdmin/RecruitBatch/initAdd', '10', '吧新增招考批次这个模块单独提取出来，这位这是一个经常使用的模块', '');
INSERT INTO `module` VALUES ('34', '0001000200020002', '正在进行的招考批次', 'recruitAdmin/RecruitBatch/index', '10', '招考批次管理', '');
INSERT INTO `module` VALUES ('36', '0001000200050001', '用人单位管理页面', 'recruitAdmin/Employer/index', '13', '', '');
INSERT INTO `module` VALUES ('37', '0001000200040001', '考生管理页面', 'recruitAdmin/Candidate/index', '12', '', '');
INSERT INTO `module` VALUES ('38', '0001000200030001', '新闻通知管理', 'recruitAdmin/Notice/index', '11', '', '');
INSERT INTO `module` VALUES ('39', '0001000200030002', '新增新闻', 'recruitAdmin/Notice/initAdd', '11', '', '');
INSERT INTO `module` VALUES ('40', '0001000200020003', '已归档的的招考批次', '', '10', '', '');
INSERT INTO `module` VALUES ('41', '000100040001', '招考管理', '', '15', '用人单位跟踪自己发布了岗位了招考批次', '');
INSERT INTO `module` VALUES ('42', '0001000400010001', '岗位列表', 'recruitAdmin/Post/index', '41', '', '');
INSERT INTO `module` VALUES ('45', '0001000200020004', '岗位列表[人事局]', 'recruitAdmin/Post/indexRSJ', '10', '', '');
INSERT INTO `module` VALUES ('46', '000100040002', '审核人员管理', '', '15', '', '');
INSERT INTO `module` VALUES ('47', '0001000400020001', '审核人员管理', 'auditor/Auditor/index', '46', '用人单位，对自己单位的审核人员进行管理', '');
INSERT INTO `module` VALUES ('48', '0001000400010002', '已发布岗位的招考批次', 'recruitAdmin/RecruitBatch/initEmployerBatch', '41', '用人单位可以查看自己曾经发布过岗位的招考批次，以便对招考批次信息的跟踪', '');
INSERT INTO `module` VALUES ('49', '0001000400010003', '审核人员安排列表', 'AuditArrange/indexForOrg', '41', '审核人员安排列表，给用人单位查看自己已经安排了的审核信息', '');
INSERT INTO `module` VALUES ('50', '0001000200020005', '考试成绩', 'ExamScore/index', '10', '', '');
INSERT INTO `module` VALUES ('51', '0001000200020006', '考场安排', 'ExamRoom/index', '10', '', '');
INSERT INTO `module` VALUES ('52', '0001000100010002', '学生管理', 'admin/Student/index', '4', '', '');

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `noticeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `noticeType` varchar(100) DEFAULT NULL COMMENT '招录通知、新闻通知、公告、政策法规、用人单位通知、考生通知、审核人员通知',
  `title` varchar(255) DEFAULT NULL,
  `content` longtext,
  `createTime` datetime DEFAULT NULL,
  `deadLineTime` datetime DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL COMMENT '新建,发布中',
  `creator` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`noticeID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('12', 'noticeType0002', 'xiwnen asdjfklsdf ', '<p>\n	asdfasdf\n</p>\n<p>\n	ajsdfj计算可得积分<s></s> \n</p>\n<p>\n	啊但是警方开始\n</p>\n<p>\n	啊四季度房价来看是发动机\n</p>\n<p>\n	啊计算可得看风景阿斯蒂芬werqwerqwerqwer\n</p>', '2014-05-22 17:12:56', '2014-06-21 17:12:56', 'noticeStatus0002', '6');

-- ----------------------------
-- Table structure for `operator`
-- ----------------------------
DROP TABLE IF EXISTS `operator`;
CREATE TABLE `operator` (
  `operatorID` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '在登录的时候使用.',
  `name` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL COMMENT '6-30位',
  `email` varchar(30) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `enable` bit(1) DEFAULT NULL,
  `department` bigint(20) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  `gender` char(2) DEFAULT NULL,
  `cardNo` char(18) NOT NULL,
  `politicStatus` varchar(100) NOT NULL COMMENT '中共党员,中共预备党员,共青团员,民革党员,民盟盟员,民建会员,民进会员,农工党党员,致公党党员,九三学社社员,台盟盟员,无党派人士,群众（现称普通公民）,港澳同胞,叛徒,反革命分子',
  PRIMARY KEY (`operatorID`),
  UNIQUE KEY `Operator_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operator
-- ----------------------------
INSERT INTO `operator` VALUES ('4', 'xx', '学校', '123', 'qwe@qq.com', '13345454', null, '9', 'ss', '男', '50023519890105423X', '民革党员');
INSERT INTO `operator` VALUES ('5', 'ddd', '徐传云', '123', '234@123.com', '123123123', null, '8', '程序员', '男', '50023519890105423X', '中共党员');
INSERT INTO `operator` VALUES ('6', 'wangsen', '王森', '123', '742937489234@qq.com', '13372662545', null, '9', '讲师', '男', '50023519890105423X', '共青团员');

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permissionID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '在同一个模块下面是唯一的。',
  `key` varchar(30) NOT NULL COMMENT '在同一个模块下面是唯一的。',
  `module` bigint(20) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`permissionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for `post`
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `postID` bigint(20) NOT NULL AUTO_INCREMENT,
  `postName` varchar(100) DEFAULT NULL,
  `recruitmentNumber` int(11) DEFAULT NULL,
  `postDes` varchar(1000) NOT NULL,
  `educationDemand` varchar(500) NOT NULL,
  `otherCondition` varchar(1000) DEFAULT NULL,
  `recruit` bigint(20) NOT NULL,
  `recruitLevel` bigint(20) NOT NULL,
  `organization` bigint(20) NOT NULL,
  `auditDate` datetime DEFAULT NULL,
  `auditOperator` bigint(20) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL COMMENT '1.新建、2.提交、3.审核通过、4.审核未通过',
  `auditMemo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`postID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('11', 'java工程师', '12', '马龙', '计算机', '方法', '14', '3', '2', null, null, 'postStatus0003', '通过');

-- ----------------------------
-- Table structure for `postlevel`
-- ----------------------------
DROP TABLE IF EXISTS `postlevel`;
CREATE TABLE `postlevel` (
  `postLevelID` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '招考类型代号001',
  `name` varchar(100) DEFAULT NULL,
  `recruitType` bigint(20) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`postLevelID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of postlevel
-- ----------------------------
INSERT INTO `postlevel` VALUES ('3', 'GWYZK0001', 'A类岗位', '1', 'A类岗位');
INSERT INTO `postlevel` VALUES ('6', 'GWYZK0002', 'B类岗位', '1', '该类型的岗位比较牛逼');

-- ----------------------------
-- Table structure for `recruitbatch`
-- ----------------------------
DROP TABLE IF EXISTS `recruitbatch`;
CREATE TABLE `recruitbatch` (
  `recruitBatchID` bigint(20) NOT NULL AUTO_INCREMENT,
  `recruitName` varchar(100) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `publishTime` datetime DEFAULT NULL,
  `jobCollectionSTime` datetime DEFAULT NULL,
  `jobCollectionETime` datetime DEFAULT NULL,
  `jobAuditSTime` datetime DEFAULT NULL,
  `jobAuditEtime` datetime DEFAULT NULL,
  `applySTime` datetime DEFAULT NULL,
  `applyETime` datetime DEFAULT NULL,
  `auditorArrangeSTime` datetime DEFAULT NULL,
  `auditorArrangeETime` datetime DEFAULT NULL,
  `auditSTime` datetime DEFAULT NULL,
  `auditETime` datetime DEFAULT NULL,
  `paymentSTime` datetime DEFAULT NULL,
  `paymentETime` datetime DEFAULT NULL,
  `printAdmissionSTime` datetime DEFAULT NULL,
  `printAdmissionETime` datetime DEFAULT NULL,
  `recruitSTime` datetime DEFAULT NULL,
  `recruitETime` datetime DEFAULT NULL,
  `roomArrangeSTime` datetime DEFAULT NULL COMMENT '考场安排开始时间',
  `roomArrangeETime` datetime DEFAULT NULL COMMENT '考场安排结束时间',
  `expense` double DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL COMMENT '新建，已发布，已发布成绩，已归档 (招考批次的其他状态都是根据时间来指定的)',
  `recruitCategory` bigint(20) DEFAULT NULL,
  `registeNotice` longtext,
  `recruitRole` longtext,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`recruitBatchID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recruitbatch
-- ----------------------------
INSERT INTO `recruitbatch` VALUES ('14', '2014年6月公务员招考', '2014-06-15 15:46:19', '2014-06-15 15:46:37', '2014-06-15 15:41:07', '2014-06-15 15:47:11', '2014-06-15 15:48:44', '2014-06-15 15:50:00', '2014-06-15 15:51:00', '2014-06-15 15:55:00', '2014-06-15 15:55:00', '2014-06-15 16:00:21', '2014-06-15 16:01:00', '2014-06-15 16:03:00', '2014-06-15 16:03:00', '2014-06-15 16:05:00', '2014-06-27 15:39:09', '2014-06-27 17:15:26', '2014-06-27 17:15:30', '2014-06-27 17:17:32', '2014-06-27 08:38:57', '2014-06-27 14:39:00', '12', 'recruitStatus0002', '1', '<p>\n	东风公司\n</p>\n<p>\n	阿斯顿丰盛的\n</p>', '<p>\n	是豆腐干豆腐干\n</p>\n<p>\n	<span>是豆腐干豆腐干</span> \n</p>\n<p>\n	<span>是豆腐干豆腐干</span> \n</p>\n<p>\n	<span>是豆腐干豆腐干<span>是豆腐干豆腐干</span></span> \n</p>', 'tree');
INSERT INTO `recruitbatch` VALUES ('15', '2014.06.17公务员招考', '2014-06-17 13:34:38', '2014-06-17 13:35:00', '2014-06-17 13:31:41', '2014-06-17 14:31:48', '2014-06-17 14:31:59', '2014-06-17 14:50:22', '2014-06-17 14:50:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '2014-06-17 14:52:22', '12', 'recruitStatus0002', '1', '凤飞飞', '凤飞飞', '任溶溶');

-- ----------------------------
-- Table structure for `recruitsignup`
-- ----------------------------
DROP TABLE IF EXISTS `recruitsignup`;
CREATE TABLE `recruitsignup` (
  `recruitSignupID` bigint(20) NOT NULL AUTO_INCREMENT,
  `recruitBatch` bigint(20) DEFAULT NULL,
  `post` bigint(20) NOT NULL,
  `candidate` bigint(20) NOT NULL,
  `status` varchar(100) NOT NULL COMMENT '草稿,提交报名,初审通过，初审未通过，复审通过，复审未通过，已缴费，已经打印准考证',
  `signupTime` datetime DEFAULT NULL,
  `firstAuditTime` datetime DEFAULT NULL,
  `secondAuditTime` datetime DEFAULT NULL,
  `payTime` datetime DEFAULT NULL,
  `printAdmissionTime` datetime DEFAULT NULL,
  `admissionNum` char(20) DEFAULT NULL COMMENT '由考务系统生成，不在这里处理',
  `firstAuditor` bigint(20) DEFAULT NULL,
  `firstAuditMemo` varchar(255) DEFAULT NULL,
  `firstAuditStatus` varchar(255) DEFAULT NULL COMMENT '审核通过，不通过',
  `reviewAuditor` bigint(20) DEFAULT NULL,
  `reviewAuditmemo` varchar(255) DEFAULT NULL,
  `reviewAuditStatus` varchar(255) DEFAULT NULL COMMENT '审核通过，不通过',
  `signupPlace` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`recruitSignupID`),
  KEY `RecruitSignup_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recruitsignup
-- ----------------------------
INSERT INTO `recruitsignup` VALUES ('1', '14', '11', '3', 'signup0008', '2014-06-15 15:53:24', '2014-06-15 16:01:53', '2014-06-15 16:02:04', '2014-06-15 16:03:32', '2014-06-27 16:24:02', null, '1', '通过', '1', '1', '通过', '1', null);

-- ----------------------------
-- Table structure for `recruittype`
-- ----------------------------
DROP TABLE IF EXISTS `recruittype`;
CREATE TABLE `recruittype` (
  `recruitTypeID` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '用于标识一个招考类型的，在报名、考场安排、上成绩的时候要用。',
  `name` varchar(100) NOT NULL,
  `createDate` datetime NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`recruitTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recruittype
-- ----------------------------
INSERT INTO `recruittype` VALUES ('1', 'GWYZK', '公务员招考', '2014-05-09 14:37:45', '公务员招考');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roleID`),
  UNIQUE KEY `Role_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('10', '考生', '');
INSERT INTO `role` VALUES ('11', '用人单位', '');
INSERT INTO `role` VALUES ('12', '人事局用户', '');
INSERT INTO `role` VALUES ('13', '系统管理员', '');

-- ----------------------------
-- Table structure for `rolemodule`
-- ----------------------------
DROP TABLE IF EXISTS `rolemodule`;
CREATE TABLE `rolemodule` (
  `roleModuleID` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` bigint(20) NOT NULL,
  `module` bigint(20) NOT NULL,
  `parent` bigint(20) NOT NULL,
  PRIMARY KEY (`roleModuleID`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rolemodule
-- ----------------------------
INSERT INTO `rolemodule` VALUES ('28', '12', '1', '0');
INSERT INTO `rolemodule` VALUES ('29', '12', '2', '1');
INSERT INTO `rolemodule` VALUES ('30', '12', '5', '2');
INSERT INTO `rolemodule` VALUES ('31', '12', '22', '5');
INSERT INTO `rolemodule` VALUES ('32', '12', '23', '5');
INSERT INTO `rolemodule` VALUES ('33', '12', '3', '1');
INSERT INTO `rolemodule` VALUES ('34', '12', '10', '3');
INSERT INTO `rolemodule` VALUES ('35', '0', '1', '0');
INSERT INTO `rolemodule` VALUES ('36', '0', '2', '1');
INSERT INTO `rolemodule` VALUES ('37', '0', '5', '2');
INSERT INTO `rolemodule` VALUES ('38', '0', '6', '2');
INSERT INTO `rolemodule` VALUES ('39', '0', '19', '6');
INSERT INTO `rolemodule` VALUES ('40', '0', '3', '1');
INSERT INTO `rolemodule` VALUES ('41', '0', '11', '3');
INSERT INTO `rolemodule` VALUES ('42', '0', '14', '1');
INSERT INTO `rolemodule` VALUES ('90', '13', '1', '0');
INSERT INTO `rolemodule` VALUES ('91', '13', '2', '1');
INSERT INTO `rolemodule` VALUES ('92', '13', '4', '2');
INSERT INTO `rolemodule` VALUES ('93', '13', '17', '4');
INSERT INTO `rolemodule` VALUES ('94', '13', '5', '2');
INSERT INTO `rolemodule` VALUES ('95', '13', '22', '5');
INSERT INTO `rolemodule` VALUES ('96', '13', '23', '5');
INSERT INTO `rolemodule` VALUES ('97', '13', '24', '5');
INSERT INTO `rolemodule` VALUES ('98', '13', '25', '5');
INSERT INTO `rolemodule` VALUES ('99', '13', '26', '5');
INSERT INTO `rolemodule` VALUES ('100', '13', '6', '2');
INSERT INTO `rolemodule` VALUES ('101', '13', '19', '6');
INSERT INTO `rolemodule` VALUES ('102', '13', '7', '2');
INSERT INTO `rolemodule` VALUES ('103', '13', '20', '7');
INSERT INTO `rolemodule` VALUES ('104', '13', '8', '2');
INSERT INTO `rolemodule` VALUES ('105', '13', '21', '8');
INSERT INTO `rolemodule` VALUES ('106', '13', '3', '1');
INSERT INTO `rolemodule` VALUES ('107', '13', '11', '3');
INSERT INTO `rolemodule` VALUES ('108', '10', '1', '0');
INSERT INTO `rolemodule` VALUES ('109', '10', '2', '1');
INSERT INTO `rolemodule` VALUES ('110', '10', '3', '1');
INSERT INTO `rolemodule` VALUES ('111', '10', '9', '3');
INSERT INTO `rolemodule` VALUES ('112', '10', '10', '3');
INSERT INTO `rolemodule` VALUES ('113', '10', '11', '3');
INSERT INTO `rolemodule` VALUES ('114', '10', '12', '3');
INSERT INTO `rolemodule` VALUES ('115', '10', '13', '3');
INSERT INTO `rolemodule` VALUES ('116', '10', '14', '1');
INSERT INTO `rolemodule` VALUES ('135', '11', '1', '0');
INSERT INTO `rolemodule` VALUES ('136', '11', '15', '1');
INSERT INTO `rolemodule` VALUES ('137', '11', '41', '15');
INSERT INTO `rolemodule` VALUES ('138', '11', '42', '41');
INSERT INTO `rolemodule` VALUES ('139', '11', '48', '41');
INSERT INTO `rolemodule` VALUES ('140', '11', '49', '41');
INSERT INTO `rolemodule` VALUES ('141', '11', '46', '15');
INSERT INTO `rolemodule` VALUES ('142', '11', '47', '46');

-- ----------------------------
-- Table structure for `rolepermission`
-- ----------------------------
DROP TABLE IF EXISTS `rolepermission`;
CREATE TABLE `rolepermission` (
  `rolePermissionID` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` bigint(20) NOT NULL,
  `module` bigint(20) NOT NULL,
  `permission` bigint(20) NOT NULL,
  PRIMARY KEY (`rolePermissionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rolepermission
-- ----------------------------

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `studentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `remark` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('5', 'aaa', 'aa', 'aa');

-- ----------------------------
-- Table structure for `systemparameter`
-- ----------------------------
DROP TABLE IF EXISTS `systemparameter`;
CREATE TABLE `systemparameter` (
  `systemParameterID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`systemParameterID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemparameter
-- ----------------------------

-- ----------------------------
-- Table structure for `userrole`
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole` (
  `userRoleID` bigint(20) NOT NULL AUTO_INCREMENT,
  `operator` bigint(20) DEFAULT NULL,
  `role` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userRoleID`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userrole
-- ----------------------------
INSERT INTO `userrole` VALUES ('25', '5', '10');
INSERT INTO `userrole` VALUES ('26', '5', '11');
INSERT INTO `userrole` VALUES ('27', '5', '12');
INSERT INTO `userrole` VALUES ('68', '6', '13');
INSERT INTO `userrole` VALUES ('69', '6', '10');
INSERT INTO `userrole` VALUES ('70', '6', '13');
INSERT INTO `userrole` VALUES ('71', '6', '11');
INSERT INTO `userrole` VALUES ('72', '6', '12');
INSERT INTO `userrole` VALUES ('73', '6', '13');
INSERT INTO `userrole` VALUES ('74', '6', '13');
