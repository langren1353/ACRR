/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50149
Source Host           : localhost:3306
Source Database       : my_mod

Target Server Type    : MYSQL
Target Server Version : 50149
File Encoding         : 65001

Date: 2016-06-04 11:53:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `acrr_norders`
-- ----------------------------
DROP TABLE IF EXISTS `acrr_norders`;
CREATE TABLE `acrr_norders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` bigint(20) DEFAULT NULL,
  `id_worker` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '订单状态，发出，接收，拒绝，结束',
  `name_sub` varchar(255) CHARACTER SET gbk DEFAULT '' COMMENT '显示名字',
  `moneytext` varchar(255) CHARACTER SET gbk DEFAULT '' COMMENT '价格',
  `tel` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `is_rapid` tinyint(1) DEFAULT NULL,
  `exptime` datetime DEFAULT NULL,
  `pubtime` datetime DEFAULT NULL,
  `addr_lat` double DEFAULT NULL,
  `addr_lon` double DEFAULT NULL,
  `addr_text` varchar(255) CHARACTER SET gbk DEFAULT NULL COMMENT '地址描述',
  `services` varchar(255) CHARACTER SET gbk DEFAULT '',
  `pic_main` varchar(255) DEFAULT NULL,
  `o_describe` varchar(5000) CHARACTER SET gbk DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of acrr_norders
-- ----------------------------
INSERT INTO `acrr_norders` VALUES ('1', '18380447743', null, '0', 'TCL电视机维修', '100以下', '18681318475', '1', '2016-06-18 09:02:01', '2016-05-04 22:28:51', '30.583094', '103.9850172013', '双流县西航港', '维修', null, '家里面电视机突然的就坏掉了');
INSERT INTO `acrr_norders` VALUES ('2', '18380447743', '15208391741', '1', '手机维修', '面谈', '18681318475', '0', '2016-06-18 09:02:01', '2016-04-08 11:28:56', '30.646852', '104.048845', '武侯大道文昌段与新苗西街交叉口旁', '维修', null, '我的手机出问题了，无法开机，没有进水');
INSERT INTO `acrr_norders` VALUES ('3', '18380447743', '15208391741', '1', '厨房挂灯维修', '面谈', '15181095536', '0', '2016-06-18 09:02:01', '2016-05-12 11:29:03', '30.685805', '104.125755', '成都 文昌路456号', '维修', '/ShopPics/shop3.jpg', '厨房挂灯闪烁，应该是线路问题');
INSERT INTO `acrr_norders` VALUES ('4', '18380447743', '15208391741', '1', '客厅风扇维修', '面谈', '85179315', '1', '2016-06-18 09:02:01', '2016-05-13 22:22:10', '30.654261', '104.017914', '太平寺东路7号（近航空四站的边上的地方）', '维修', '/ShopPics/shop4.jpg', '风扇转动一会儿就不转了');
INSERT INTO `acrr_norders` VALUES ('5', '18380447743', null, '0', '冰箱维修', '面谈', '18681318475', '0', '2016-06-18 09:02:01', '2016-05-14 11:30:00', '30.608668', '103.950836', '时代·奥特莱斯1层B区1015号', '维修', '/ShopPics/shop5.jpg', '冰箱不能制冷了，好奇怪');
INSERT INTO `acrr_norders` VALUES ('6', '18380447743', '15208391741', '1', '家中漏水维修', '面谈', '18681318475', '0', '2016-06-18 09:02:01', '2016-05-02 02:30:04', '30.540372', '104.07506', '成都望景横街与望景中街交叉口旁', '维修', '/ShopPics/shop6.jpg', '楼上厨房有点漏水情况');
INSERT INTO `acrr_norders` VALUES ('7', '18380447743', '15208391741', '1', '路由器连不上网', '50以下', '18681318475', '1', '2016-06-18 09:02:01', '2016-05-05 11:03:04', '30.712968', '104.081792', '七里路与商业街交叉口东', '服务', null, '路由器怎么设置才能联网了，希望有人指导');
INSERT INTO `acrr_norders` VALUES ('8', '18380447743', null, '0', '电脑重装需求', '面谈', '85031351', '0', '2016-06-18 09:02:01', '2016-05-13 06:34:05', '30.711384', '104.041802', '双流机场路东路段158号码', '服务', '/ShopPics/shop8.jpg', '我电脑中病毒了，据说需要重装，有没有人帮我重装下电脑啊');
INSERT INTO `acrr_norders` VALUES ('9', '18380447743', '15208391741', '1', 'windows平板损坏', '面谈', '18980896227', '0', '2016-06-18 09:02:01', '2016-06-01 14:30:06', '30.646852', '104.048845', '双流机场路土桥段158号', '服务', '/ShopPics/shop9.jpg', '我平板掉地上屏幕坏了，修一下需要多少钱啊');
INSERT INTO `acrr_norders` VALUES ('10', '18380447743', null, '0', '落地风扇不转了', '面谈', '13880823998', '1', '2016-06-18 09:02:01', '2016-05-02 11:56:06', '30.540372', '104.07506', '成都簇桥横街协卓医疗旁', '维修', null, '风扇不转了');
INSERT INTO `acrr_norders` VALUES ('11', '18380447743', null, '0', '音响问题', '面谈', '13882026923', '0', '2016-06-18 09:02:01', '2016-05-07 07:04:06', '30.712968', '104.081792', '西航港新街与观音阁街交叉口旁', '维修', null, '我家音响总是发出咔咔的噪声');
INSERT INTO `acrr_norders` VALUES ('12', '18380447743', null, '0', '三轮车不能启动', '面谈', '13982109286', '0', '2016-06-18 09:02:01', '2016-05-29 22:30:08', '30.711384', '104.041802', '航渡大街与商都路交叉口南300米', '维修', null, '摩托三轮车发动不了');
INSERT INTO `acrr_norders` VALUES ('13', '18380447743', null, '0', '网线损坏找人帮忙', '面谈', '18280263137', '1', '2016-06-18 09:02:01', '2016-05-29 22:30:08', '30.646852', '104.048845', '成都簇桥望锦东街100号', '维修', '/ShopPics/shop13.jpg', '网线断了，希望带上工具帮个忙');
INSERT INTO `acrr_norders` VALUES ('14', '18380447743', null, '0', '橱柜简易装修', '面谈', '13438875633', '0', '2016-06-18 09:02:01', '2016-05-29 22:30:09', '30.685805', '104.125755', '航港路1313号', '维修', '/ShopPics/shop14.jpg', '橱柜装修，带价的来');
INSERT INTO `acrr_norders` VALUES ('15', '18380447743', null, '0', '电脑不能上网了', '50以下', '18681318475', '0', '2016-06-18 09:02:01', '2016-05-29 22:30:10', '30.654261', '104.017914', '学府润园北街133号2栋', '服务', '/ShopPics/shop15.jpg', '电脑怎么设置都不能上网，又没有人来帮帮忙啊');
INSERT INTO `acrr_norders` VALUES ('16', '18380447743', null, '0', '楼梯口声控灯维修', '面谈', '18681318475', '1', '2016-06-18 09:02:01', '2016-05-29 22:30:11', '30.711384', '104.041802', '成烨路悦华旅馆东侧', '维修', null, '楼梯口声控灯不亮了，帮忙修一下啊');
INSERT INTO `acrr_norders` VALUES ('17', '18380447743', null, '0', '电视机不能上网了', '面谈', '4000557774', '0', '2016-06-18 09:02:01', '2016-05-29 22:30:14', '30.540372', '104.07506', '天府软件园A区（世纪地铁站）', '服务', '/ShopPics/shop17.jpg', '机顶盒子怎么联网啊');
INSERT INTO `acrr_norders` VALUES ('18', '18380447743', null, '0', '水管漏水维修', '面谈', '13880892881', '0', '2016-06-18 09:02:01', '2016-05-29 22:30:15', '30.712968', '104.081792', '成都五大花园金燕路118号附5号', '维修', '/ShopPics/shop18.jpg', '水管漏水，堵不住了。。');
INSERT INTO `acrr_norders` VALUES ('19', '18380447743', null, '0', '吊扇声音太大了', '面谈', '13438025010', '1', '2016-06-18 09:02:01', '2016-05-29 22:30:15', '30.711384', '104.041802', '瑞升南路街6号', '维修', '/ShopPics/shop19.jpg', '吊扇声音太大了，晚上睡觉的时候太吵了');
INSERT INTO `acrr_norders` VALUES ('20', '18380447743', '15208391741', '1', '电脑安装软件', '面谈', '18681318475', '0', '2016-06-18 09:02:01', '2016-05-29 22:30:17', '30.646852', '104.048845', '成都家园路8号', '服务', null, 'PhotoShop怎么安装啊，有没有人来指导一下');
INSERT INTO `acrr_norders` VALUES ('23', '15208391741', null, '1', 'php毕业项目求助', '100以下', '15208391741', '1', '2016-06-02 18:43:23', '2016-06-02 11:13:44', '30.583265', '103.984489', '四川省成都市双流区锦华路四段136号', '服务', null, '我朋友帮我做的php项目不能用了，明天就要答辩了，找个懂php的哥们来帮我指导以下');
INSERT INTO `acrr_norders` VALUES ('24', '15208391741', '15208391741', '1', 'java代码求解', '100以下', '15208391741', '1', '2016-06-02 22:00:32', '2016-06-02 11:36:40', '30.581178', '103.988746', '四川省成都市双流区成都信息工程大学航空港校区', '服务', null, '很难');
INSERT INTO `acrr_norders` VALUES ('28', '15208391741', null, '0', '我要吃好吃的', '30以下', '15208391741', '0', '2016-06-03 11:13:00', '2016-06-03 11:13:32', '30.580089', '103.988807', '四川省成都市双流区学府路一段', '服务', null, '好饿呀，我要吃好吃的');

-- ----------------------------
-- Table structure for `acrr_token`
-- ----------------------------
DROP TABLE IF EXISTS `acrr_token`;
CREATE TABLE `acrr_token` (
  `uid` bigint(20) NOT NULL DEFAULT '0',
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of acrr_token
-- ----------------------------
INSERT INTO `acrr_token` VALUES ('15208391741', 'CFCD208495D565EF66E7DFF9F98764DA');

-- ----------------------------
-- Table structure for `acrr_user`
-- ----------------------------
DROP TABLE IF EXISTS `acrr_user`;
CREATE TABLE `acrr_user` (
  `idtel` bigint(11) NOT NULL DEFAULT '0' COMMENT '电话号码',
  `name` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `email` varchar(24) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL,
  `udescribe` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `usex` varchar(255) CHARACTER SET gbk DEFAULT NULL,
  `ubirth` date DEFAULT NULL,
  PRIMARY KEY (`idtel`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of acrr_user
-- ----------------------------
INSERT INTO `acrr_user` VALUES ('15208391741', '黄师傅啊', '25F9E794323B453885F5181F1B624D0B', '123456@qq.com', '/headPort/9FD3D4298EF7D36BB7BC6DBDEE3E1DCE.jpg', '专业维修就是我', '女', '1994-10-07');
INSERT INTO `acrr_user` VALUES ('18380447743', '李四', '25F9E794323B453885F5181F1B624D0B', '123456@qq.com', '/headPort/15208391741.jpg', '我的个人简介', '女', '1994-11-22');
