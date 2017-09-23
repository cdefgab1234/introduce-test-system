/*
SQLyog Ultimate v8.32 
MySQL - 5.0.27-community : Database - huaxin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`huaxin` /*!40100 DEFAULT CHARACTER SET gbk */;

USE `huaxin`;

/*Table structure for table `choice` */

DROP TABLE IF EXISTS `choice`;

CREATE TABLE `choice` (
  `quesid` int(20) NOT NULL auto_increment,
  `detail` varchar(255) NOT NULL default '',
  `A` varchar(255) default '',
  `B` varchar(255) default '',
  `C` varchar(255) default '',
  `D` varchar(255) default '',
  `quesanswer` varchar(10) NOT NULL default '',
  `diffculty` varchar(10) NOT NULL,
  `position` varchar(30) NOT NULL default '',
  `kpoint` varchar(20) NOT NULL,
  `type` varchar(30) NOT NULL default '',
  PRIMARY KEY  (`quesid`),
  UNIQUE KEY `id` (`quesid`),
  KEY `FK_choice` (`position`),
  CONSTRAINT `FK_choice` FOREIGN KEY (`position`) REFERENCES `profession` (`pos`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `choice` */

insert  into `choice`(`quesid`,`detail`,`A`,`B`,`C`,`D`,`quesanswer`,`diffculty`,`position`,`kpoint`,`type`) values (1,'地球是什么形状的','圆的','椭圆','三角','正方形','B','简单','逻辑','逻辑性','选择题'),(2,'1+1=？','2','3','王','中等','A','中等','逻辑','逻辑性','选择题'),(3,'选出不同类的一项：','蛇','树','老虎','简单','正确','简单','逻辑','逻辑性','判断题'),(4,'什么球最大？','地球','月球','足球','眼球','D','中等','逻辑','逻辑性','选择题'),(5,'你愁啥,最有效的回答','瞅你咋的','啥也没愁','默默低头转身走','中等','C','中等','逻辑','逻辑性','选择题'),(6,'下面哪项不是java面向对象的三大特性','封装','多态','继承','简单','A','简单','java','基础','选择题'),(7,'在Java中，负责对字节代码解释执行的是  ','应用服务器','虚拟机','垃圾回收器','编译器','B','简单','java','基础','选择题'),(8,'一个栈的输入序列为1 2 3 4 5，则下列序列中不可能是栈得输出序列的是',' 5 4 1 3 2','. 2 3 4 1 5','1 5 4 3 2',' 2 3 1 4 5','A','简单','java','基础','选择题'),(9,'LDAP是什么?  ','是一种开源产品',' 是一种编程语言',' 是一种访问协议','简单','C','简单','java','基础','选择题'),(10,' 要想在你的视图上成功的执行查询需要做什么？   ','只能在基础表中有select权限','在视图中需要有select权限','基础表中必须有数据','简单','B','简单','java','基础','选择题'),(11,'下面关于该组语句的哪个描述是正确的？','DESCRIBE DEPT语句将返回一个错误ORA-04043: object DEPT does not exist.',' DESCRIBE DEPT语句将显示DEPT表的结构描述内容','DESCRIBE DEPT语句将只有在ROLLBACK之前引入一个COMMIT语句时，才会显示DEPT表的结构描述内容','ROLLBACK语句将释放DEPT占用的存储空间','B','简单','java','基础','选择题'),(12,'下列哪一个选项按照顺序包括了OSI模型的七个层次','物理层 数据链路层 传输层 网络层 会话层 表示层 应用层',' 物理层 数据链路层 会话层 网络层 传输层 表示层 应用层','物理层 数据链路层 网络层 传输层 会话层 表示层 应用层','网络层 传输层 物理层 数据链路层 会话层 表示层 应用层','C','一般','java','基础','选择题'),(13,'以下哪些不是javascript的全局函数',' eval',' escape','setTimeout','parseFloat','C','一般','java','基础','选择题'),(14,'下面有关系统并发访问数估算数据那个最有效：','高峰时段日处理业务量100000','高峰时段平均每秒请求数80','同时在线用户数100','平均每秒用户请求数50','B','困难','java','基础','选择题'),(15,'PL/SQL中用来判断FETCH语句是否成功，并且在FETCH语句失败时返回逻辑真的属性是','%ISOPEN','%NOTFOUND','%ROWCOUNT','%FOUND','B','简单','java','基础','选择题'),(16,'在实现DAO设计模式时，下面哪种模式经常被采用：','Proxy模式',' Factory模式',' Prototype模式','Observer模式','B','简单','java','基础','选择题'),(17,'下面哪个Set是排序的？','LinkedHashSet',' HashSet','AbstractSet','TreeSet','A','简单','java','基础','选择题'),(18,'类的实例方法表示的是什么？','父类对象的行为','类的属性','类对象的行为','类的行为','C','简单','java','基础','选择题'),(120,'测试数据1-判断',NULL,NULL,NULL,NULL,'正确','简单','逻辑','逻辑性','判断题'),(121,'测试数据2-判断',NULL,NULL,NULL,NULL,'正确','简单','ui','逻辑性','判断题'),(122,'测试数据3-判断',NULL,NULL,NULL,NULL,'错误','简单','java','逻辑性','判断题'),(123,'测试数据4-判断',NULL,NULL,NULL,NULL,'正确','简单','逻辑','逻辑性','判断题'),(124,'测试数据5-判断',NULL,NULL,NULL,NULL,'错误','简单','逻辑','逻辑性','判断题'),(125,'测试数据6-判断',NULL,NULL,NULL,NULL,'正确','简单','java','逻辑性','判断题'),(126,'测试数据7-判断',NULL,NULL,NULL,NULL,'错误','简单','逻辑','逻辑性','判断题'),(127,'测试数据8-判断',NULL,NULL,NULL,NULL,'正确','简单','ui','逻辑性','判断题'),(128,'测试数据9-选择题','测试数据A','测试数据B','测试数据C','测试数据D','A','简单','java','基础','选择题'),(129,'测试数据10-选择题','测试数据A','测试数据B','测试数据C','测试数据D','B','简单','java','基础','选择题'),(130,'测试数据11-选择题','测试数据A','测试数据B','测试数据C','测试数据D','B','简单','ui','基础','选择题'),(131,'测试数据12-选择题','测试数据A','测试数据B','测试数据C','测试数据D','A','简单','java','基础','选择题'),(132,'测试数据13-选择题','测试数据A','测试数据B','测试数据C','测试数据D','C','简单','java','基础','选择题'),(133,'测试数据14-选择题','测试数据A','测试数据B','测试数据C','测试数据D','D','简单','逻辑','基础','选择题'),(134,'测试数据15-选择题','测试数据A','测试数据B','测试数据C','测试数据D','A','简单','逻辑','基础','选择题'),(135,'测试数据16-选择题','测试数据A','测试数据B','测试数据C','测试数据D','C','简单','java','基础','选择题'),(136,'测试数据17-选择题','测试数据A','测试数据B','测试数据C','测试数据D','A','简单','java','基础','选择题'),(137,'测试数据18-选择题','测试数据A','测试数据B','测试数据C','测试数据D','B','简单','java','基础','选择题'),(138,'测试数据19-选择题','测试数据A','测试数据B','测试数据C','测试数据D','B','简单','ui','基础','选择题'),(139,'测试数据20-选择题','测试数据A','测试数据B','测试数据C','测试数据D','A','简单','java','基础','选择题'),(140,'测试数据21-选择题','测试数据A','测试数据B','测试数据C','测试数据D','C','简单','java','基础','选择题'),(141,'测试数据22-选择题','测试数据A','测试数据B','测试数据C','测试数据D','D','简单','逻辑','基础','选择题'),(142,'测试数据23-选择题','测试数据A','测试数据B','测试数据C','测试数据D','ABCD','简单','逻辑','基础','多选题'),(143,'测试数据24-选择题','测试数据A','测试数据B','测试数据C','测试数据D','CD','简单','java','基础','多选题'),(144,'测试数据25-选择题','测试数据A','测试数据B','测试数据C','测试数据D','ACD','简单','java','基础','多选题'),(145,'测试数据26-选择题','测试数据A','测试数据B','测试数据C','测试数据D','B','简单','java','基础','多选题'),(146,'测试数据27-选择题','测试数据A','测试数据B','测试数据C','测试数据D','AC','简单','ui','基础','多选题'),(147,'测试数据28-选择题','测试数据A','测试数据B','测试数据C','测试数据D','ABC','简单','java','基础','多选题'),(148,'测试数据30-选择题','测试数据A','测试数据B','测试数据C','测试数据D','CD','简单','逻辑','基础','多选题'),(149,'测试数据31-选择题','测试数据A','测试数据B','测试数据C','测试数据D','BC','简单','逻辑','基础','多选题'),(150,'测试数据32-选择题','测试数据A','测试数据B','测试数据C','测试数据D','C','简单','java','基础','选择题');

/*Table structure for table `interview` */

DROP TABLE IF EXISTS `interview`;

CREATE TABLE `interview` (
  `userid` int(20) NOT NULL auto_increment,
  `username` varchar(20) NOT NULL,
  `userphone` varchar(20) NOT NULL,
  `usersource` varchar(20) NOT NULL,
  `email` varchar(30) NOT NULL,
  `position` varchar(30) NOT NULL default '',
  `score` int(30) default NULL,
  `costtime` varchar(30) default NULL,
  `questionsdifficulty` varchar(30) NOT NULL default '',
  `qusetionCount` varchar(40) NOT NULL default '',
  `otherPos` varchar(1000) default NULL,
  `phoneYzState` varchar(20) NOT NULL default 'true',
  PRIMARY KEY  (`userid`),
  KEY `FK_interview` (`position`),
  CONSTRAINT `FK_interview` FOREIGN KEY (`position`) REFERENCES `profession` (`pos`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `interview` */

insert  into `interview`(`userid`,`username`,`userphone`,`usersource`,`email`,`position`,`score`,`costtime`,`questionsdifficulty`,`qusetionCount`,`otherPos`,`phoneYzState`) values (1,'王文涛','18149362781','铜川','123@qq.com','java',0,'1','简单','20','{\"position\":\"逻辑\",\"count\":\"5\",\"thisdiffcult\":\"简单\"}','false'),(2,'梁建','15619036292','陕西','5456@qq.com','java',0,'1','简单','15','{\"position\":\"逻辑\",\"count\":\"5\",\"thisdiffcult\":\"简单\"}','false'),(3,'辛旭初','18089273154','啦啦','cdefgab1234@126.com','java',0,'1','简单','20','{\"position\":\"逻辑\",\"count\":\"5\",\"thisdiffcult\":\"简单\"}','false'),(4,'李亚萌','17053005952','家里蹲','cdefgab1234@126.com','java',0,'1','简单','20','{\"position\":\"逻辑\",\"count\":\"5\",\"thisdiffcult\":\"简单\"}','false'),(7,'王文涛','18149362780','铜川','123@qq.com','逻辑',0,'2','简单','20','{\"position\":\"java\",\"count\":\"10\",\"thisdiffcult\":\"简单\"}','false'),(8,'张文艳','17629212871','爱上大叔点','爱仕达爱仕达','逻辑',0,'1','简单','15','{\"position\":\"java\",\"count\":\"10\",\"thisdiffcult\":\"简单\"}','false'),(9,'吴光鑫','15249212962','爱上大叔点','爱上大叔点','逻辑',5,'1','简单','15','{\"position\":\"java\",\"count\":\"10\",\"thisdiffcult\":\"简单\"}','false'),(10,'薛嘉美','17691187872','爱上大叔点','爱上大叔点','逻辑',0,'1','简单','15','{\"position\":\"java\",\"count\":\"10\",\"thisdiffcult\":\"简单\"}','false'),(11,'范卓','15891300969','爱上大叔点','爱上大叔点','逻辑',15,'1','简单','15','{\"position\":\"java\",\"count\":\"10\",\"thisdiffcult\":\"简单\"}','false'),(12,'贾江梅','18729720363','爱上大叔点','1163646729@qq.com','逻辑',0,'2','简单','15','{\"position\":\"java\",\"count\":\"10\",\"thisdiffcult\":\"简单\"}','false'),(13,'陈雅楠','15091327753','爱上大叔点','爱上大叔点','逻辑',0,'1','简单','15','{\"position\":\"java\",\"count\":\"10\",\"thisdiffcult\":\"简单\"}','false'),(14,'何冲','13088965892','爱上大叔点','爱上大叔点','逻辑',NULL,NULL,'简单','15','{\"position\":\"java\",\"count\":\"10\",\"thisdiffcult\":\"简单\"}','false'),(15,'笨嘴先生空空','15388616990','爱上大叔点','爱上大叔点','逻辑',25,'1','简单','15','{\"position\":\"java\",\"count\":\"10\",\"thisdiffcult\":\"简单\"}','false');

/*Table structure for table `parper` */

DROP TABLE IF EXISTS `parper`;

CREATE TABLE `parper` (
  `userid` int(11) NOT NULL,
  `quesid` int(20) NOT NULL,
  `useranswer` varchar(30) NOT NULL,
  `answerResult` varchar(30) default NULL,
  PRIMARY KEY  (`userid`,`quesid`),
  KEY `FK_parper1` (`quesid`),
  CONSTRAINT `FK_parper1` FOREIGN KEY (`quesid`) REFERENCES `choice` (`quesid`),
  CONSTRAINT `FK_parper111` FOREIGN KEY (`userid`) REFERENCES `interview` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `parper` */

insert  into `parper`(`userid`,`quesid`,`useranswer`,`answerResult`) values (11,1,'C','false'),(11,3,'错误','false'),(11,6,'C','false'),(11,7,'C','false'),(11,8,'C','false'),(11,9,'B','false'),(11,120,'正确','true'),(11,122,'正确','false'),(11,123,'错误','false'),(11,124,'错误','true'),(11,125,'错误','false'),(11,126,'正确','false'),(11,133,'D','true'),(11,134,'C','false'),(11,141,'C','false'),(11,142,'C','false'),(11,143,'','false'),(11,144,'','false'),(11,145,'','false'),(11,147,'错误','false'),(11,148,'B','false'),(11,149,'A','false'),(15,1,'D','false'),(15,3,'错误','false'),(15,6,'D','false'),(15,7,'D','false'),(15,8,'D','false'),(15,9,'D','false'),(15,120,'错误','false'),(15,122,'错误','true'),(15,123,'错误','false'),(15,124,'错误','true'),(15,125,'错误','false'),(15,126,'错误','true'),(15,133,'D','true'),(15,134,'D','false'),(15,141,'D','true'),(15,142,'D','false'),(15,143,'D','false'),(15,144,'D','false'),(15,145,'D','false'),(15,147,'错误','false'),(15,148,'D','false'),(15,149,'D','false');

/*Table structure for table `powerstate` */

DROP TABLE IF EXISTS `powerstate`;

CREATE TABLE `powerstate` (
  `powerid` int(30) NOT NULL auto_increment,
  `powermodel` varchar(30) NOT NULL,
  `powerdetail` varchar(30) NOT NULL,
  `edittype` varchar(20) NOT NULL,
  PRIMARY KEY  (`powerid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `powerstate` */

insert  into `powerstate`(`powerid`,`powermodel`,`powerdetail`,`edittype`) values (1,'用户管理','手机号','replace'),(2,'用户管理','邮箱','replace'),(3,'用户管理','身份证','replace'),(4,'用户管理','删除','isshow'),(5,'面试管理','添加','isshow'),(6,'面试管理','删除','isshow'),(7,'面试管理','手机号','replace'),(8,'面试管理','来源','replace'),(9,'面试管理','邮箱','replace'),(10,'面试管理','专业','isshow'),(11,'专业管理','添加','isshow'),(12,'专业管理','删除','isshow'),(13,'专业管理','创建时间','replace'),(14,'专业管理','试题数量','replace'),(15,'试题管理','添加','isshow'),(16,'试题管理','修改','isshow'),(17,'试题管理','删除','isshow'),(18,'试题管理','查询','isshow'),(19,'试题管理','问题','replace'),(20,'试题管理','类型','replace'),(21,'统计管理','查询','isshow'),(22,'统计管理','来源','replace'),(23,'统计管理','专业','replace'),(24,'统计管理','分数','replace'),(25,'统计管理','考试用时','replace'),(26,'面试管理','修改','isshow'),(27,'试题管理','导入','isshow'),(28,'试题管理','导出','isshow'),(29,'统计管理','导出','isshow');

/*Table structure for table `profession` */

DROP TABLE IF EXISTS `profession`;

CREATE TABLE `profession` (
  `pos` varchar(30) NOT NULL default '',
  `edittime` varchar(40) NOT NULL,
  PRIMARY KEY  (`pos`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `profession` */

insert  into `profession`(`pos`,`edittime`) values ('java','2017-09-06 17:23:01'),('ui','2017-09-06 17:23:08'),('测试专业','2017-09-08 16:25:48'),('逻辑','2017-09-06 17:21:39');

/*Table structure for table `security` */

DROP TABLE IF EXISTS `security`;

CREATE TABLE `security` (
  `yzid` int(11) NOT NULL auto_increment,
  `Security` varchar(300) default NULL,
  `type` varchar(300) default NULL,
  PRIMARY KEY  (`yzid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `security` */

insert  into `security`(`yzid`,`Security`,`type`) values (1,'你喜欢什么颜色','0'),(2,'你喜欢什么城市','0'),(3,'你小学老师是谁','0'),(4,'你爸爸叫什么','0'),(5,'你的爱好是什么','0'),(6,'你最难忘的人或事','0'),(7,'你最喜欢的明星','0'),(10,'你的前女友是','0');

/*Table structure for table `securityuser` */

DROP TABLE IF EXISTS `securityuser`;

CREATE TABLE `securityuser` (
  `nameid` int(11) NOT NULL,
  `yzid` int(11) NOT NULL,
  `answer` varchar(300) default NULL,
  PRIMARY KEY  (`nameid`,`yzid`),
  KEY `FK_securityuser11` (`yzid`),
  CONSTRAINT `FK_securityuser` FOREIGN KEY (`nameid`) REFERENCES `user` (`nameid`),
  CONSTRAINT `FK_securityuser11` FOREIGN KEY (`yzid`) REFERENCES `security` (`yzid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `securityuser` */

insert  into `securityuser`(`nameid`,`yzid`,`answer`) values (1,1,'黑'),(1,3,'自己'),(1,5,'你猜'),(2,1,'蓝色'),(2,2,'西安'),(2,5,'lol'),(4,1,'黑色'),(4,2,'西安'),(4,3,'小明'),(5,1,'1'),(5,2,'3'),(5,3,'2'),(6,1,'red'),(6,2,'ss'),(6,3,'ss'),(7,1,'white'),(7,2,'xa'),(7,5,'computer');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `nameid` int(11) NOT NULL auto_increment,
  `pass` varchar(300) default NULL,
  `name` varchar(300) default NULL,
  `sfzid` varchar(60) default NULL,
  `phone` varchar(60) default NULL,
  `email` varchar(60) default NULL,
  `logo` varchar(10) default '1',
  `powerstate` int(10) NOT NULL default '1',
  PRIMARY KEY  (`nameid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `user` */

insert  into `user`(`nameid`,`pass`,`name`,`sfzid`,`phone`,`email`,`logo`,`powerstate`) values (1,'-139695783799064248343851432038745571054','liangjian','610222199111111545','15619036292','994415585@qq.com','5',1),(2,'-75952519287552397355641582358819463047','xinxuchu','61022219911121','13572034214','291684082@qq.com','6',1),(4,'-139695783799064248343851432038745571054','admin','610222199111041111','13111111113','zwy_qq@163.com','1',1),(5,'-49411773451514233862806653986310096146','xxxxx',NULL,'18729720363','xxxxx@qq.com','4',1),(6,'-89353568381126298296513862537595974601','ttttt',NULL,'15091327753','tttttt@qq.com','1',1),(7,'139316540670622790131747281639799926638','teacher',NULL,'18311111111','ceshi@vip.com','5',1);

/*Table structure for table `userpower` */

DROP TABLE IF EXISTS `userpower`;

CREATE TABLE `userpower` (
  `nameid` int(30) NOT NULL,
  `powerid` int(30) NOT NULL,
  PRIMARY KEY  (`nameid`,`powerid`),
  KEY `FK_userpowe11` (`powerid`),
  CONSTRAINT `FK_userpowe1` FOREIGN KEY (`nameid`) REFERENCES `user` (`nameid`),
  CONSTRAINT `FK_userpowe11` FOREIGN KEY (`powerid`) REFERENCES `powerstate` (`powerid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `userpower` */

insert  into `userpower`(`nameid`,`powerid`) values (1,1),(2,1),(4,1),(7,1),(1,2),(2,2),(4,2),(7,2),(1,3),(2,3),(4,3),(7,3),(1,4),(2,4),(4,4),(7,4),(1,5),(2,5),(4,5),(7,5),(1,6),(2,6),(4,6),(7,6),(1,7),(2,7),(4,7),(7,7),(1,8),(2,8),(4,8),(7,8),(1,9),(2,9),(4,9),(7,9),(1,10),(2,10),(4,10),(7,10),(1,11),(2,11),(4,11),(7,11),(1,12),(2,12),(4,12),(7,12),(1,13),(2,13),(4,13),(7,13),(1,14),(2,14),(4,14),(7,14),(1,15),(2,15),(4,15),(7,15),(1,16),(2,16),(4,16),(7,16),(1,17),(2,17),(4,17),(7,17),(2,18),(4,18),(7,18),(2,19),(4,19),(7,19),(2,20),(4,20),(7,20),(2,21),(4,21),(7,21),(2,22),(4,22),(7,22),(1,23),(2,23),(4,23),(7,23),(1,24),(2,24),(4,24),(7,24),(1,25),(2,25),(4,25),(7,25),(1,26),(2,26),(4,26),(7,26),(1,27),(2,27),(4,27),(7,27),(1,28),(2,28),(4,28),(7,28),(1,29),(2,29),(4,29),(7,29);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
