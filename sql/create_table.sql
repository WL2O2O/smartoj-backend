# 数据库初始化
# @author <a href="https://github.com/wl2o2o">程序员CSGUIDER</a>
# @from <a href="https://wl2o2o.github.io">CSGUIDER博客</a>

-- 创建库
create database if not exists smartoj;

-- 切换库
use smartoj;

-- 用户表
CREATE TABLE if not exists user (
  id        	     bigint           auto_increment                          comment 'id'   	 primary key,
  userAccount        varchar(256)  								  not null    comment '用户账号',
  userPassword       varchar(512)						          not null    comment '用户密码',
  userName 		     varchar(256)  								  null 	      comment '用户昵称',
  userAvatar 	     varchar(1024) 								  null 	      comment '用户头像',
  userProfile        varchar(512)							 	  null        comment '用户简介',
  userRole 	 	     varchar(256)     default 'user'			  not null    comment '用户角色',
  unionId			 varchar(256)								  null		  comment '微信开放平台id',
  mpOpenId 		     varchar(256)					   			  null 	      comment '微信公众号id',
  createTime	     datetime         default CURRENT_TIMESTAMP   not null	  comment '创建时间',
  updateTime         datetime         default CURRENT_TIMESTAMP   not null    on update CURRENT_TIMESTAMP comment '更新时间',
  isDelete           tinyint          default 0					  not null    comment '逻辑删除',
  index 			 idx_unionId      (unionId)
) comment '用户表'  collate = utf8mb4_unicode_ci;


-- 字段：
-- 题目相关：id、标题、内容、标签、答案、提交数量、通过数量、通关率（扩展）；
-- 判题相关：判题用例（judgeCase / json）、判题限制（judgeConfig / json）、判题类型（扩展）；
-- 题目表
CREATE TABLE if not exists question (
  id        	     bigint        	  auto_increment                         comment '题目id'     primary key,
  userId        	 bigint        								 not null    comment '创建用户id',
  title			     varchar(256)  								 not null    comment '题目标题',
  tags 			     varchar(256)				    		 	 not null    comment '题目标签',
  content            text							        	 not null    comment '题目内容',
  answer	 	  	 text				  						 null 	     comment '题目答案',
  judgeCase	 	     text 								     	 not null    comment '判题用例',
  judgeConfig	     text									     null		 comment '判题限制',
  submitNum 	     int 			  default 0					 null 	     comment '提交数量',
  acceptNum          int         	  default 0	 			  	 null        comment '通过数量',
  thumbNum 		     int			  default 0					 null 	     comment '点赞数量',
  favourNum		     int 	   		  default 0					 null 	     comment '收藏数量',
  createTime	     datetime         default CURRENT_TIMESTAMP  not null	 comment '创建时间',
  updateTime        datetime          default CURRENT_TIMESTAMP  not null   on update CURRENT_TIMESTAMP comment '更新时间',
  isDelete           tinyint          default 0                  not null    comment '逻辑删除',
  index 			 idx_userId       (userId)
) comment '题目表'  collate = utf8mb4_unicode_ci;


-- 字段：用户id、题目id、语言、代码、判题状态、判题信息（judgeInfo / json）;
-- 题目提交表
CREATE TABLE if not exists question_submit (
  id                bigint          auto_increment                           comment 'id'		primary key,
  userId            bigint                                      not null     comment '创建用户 id',
  questionId        bigint                                      not null     comment '题目 id',
  language          varchar(128)                                not null     comment '编程语言',
  code              text                                        not null     comment '用户代码',
  status            int             default 0                   not null     comment '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
  judgeInfo         text                                        null         comment '判题信息（json 对象）',
  createTime        datetime        default CURRENT_TIMESTAMP   not null     comment '创建时间',
  updateTime        datetime        default CURRENT_TIMESTAMP   not null     on update CURRENT_TIMESTAMP comment '更新时间',
  isDelete          tinyint         default 0                   not null     comment '是否删除',
  index             idx_questionId  (questionId),
  index             idx_userId      (userId)

) comment '题目提交表'  collate = utf8mb4_unicode_ci;


