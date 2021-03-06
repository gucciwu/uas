drop table IF EXISTS UAS_USER;
create table UAS_USER
(
  ID          BIGINT PRIMARY KEY AUTO_INCREMENT,
  JOB_NUMBER        VARCHAR(50) not null,
  USER_NAME         VARCHAR(50),
  STATUS            SMALLINT default 1 not null,
  CREATE_TIME       DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  MODIFY_TIME      DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  ID_NUMBER        VARCHAR(50),
  ID_TYPE          SMALLINT,
  NAME             VARCHAR(120),
  ORG_ID           BIGINT not null,
  ORG_TYPE         SMALLINT NULL DEFAULT 1,
  TEL              VARCHAR(40),
  MOBILE           VARCHAR(15),
  EMAIL            VARCHAR(50),
  LOGIN_COUNT       INT default 0,
  LAST_LOGIN_IP     VARCHAR(50),
  LAST_LOGIN_INFO    VARCHAR(150),
  unique(JOB_NUMBER),
  unique(ID_NUMBER, ID_TYPE)
);

drop table IF EXISTS UAS_PASSWORD;
create table UAS_PASSWORD
(
  ID          BIGINT PRIMARY KEY AUTO_INCREMENT,
  USER_ID        BIGINT not null,
  PASSWORD       VARCHAR(100) not null,
  MODIFY_TIME      DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE(USER_ID)
);

drop table IF EXISTS UAS_ACCOUNT;
create table UAS_ACCOUNT
(
  ID               BIGINT PRIMARY KEY AUTO_INCREMENT,
  ACCOUNT_ID        VARCHAR(50) not null,
  ACCOUNT_TYPE      SMALLINT not null,
  STATUS           SMALLINT not null,
  CREATE_TIME       DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  MODIFY_TIME      DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  USER_ID          BIGINT,
  UNIQUE(ACCOUNT_ID,ACCOUNT_TYPE)
);

drop table IF EXISTS UAS_APP;
create table UAS_APP
(
  ID             BIGINT PRIMARY KEY AUTO_INCREMENT,
  NAME           VARCHAR(100),
  SECRET         VARCHAR(100),
  ORG_TYPE       SMALLINT,
  PATH           VARCHAR(250),
  COMMENT           VARCHAR(250)
);

drop table IF EXISTS UAS_APP_ACCOUNT;
create table UAS_APP_ACCOUNT
(
  ID             BIGINT PRIMARY KEY AUTO_INCREMENT,
  APP_ID          BIGINT,
  ORG_ID          BIGINT NOT NULL,
  ORG_TYPE        SMALLINT NULL DEFAULT 1,
  USER_ID         BIGINT,
  JOB_NUMBER      VARCHAR(50),
  ACCOUNT         VARCHAR(100),
  UNIQUE (APP_ID,USER_ID),
  UNIQUE (APP_ID,JOB_NUMBER)
);

drop table IF EXISTS UAS_ORG;
create table UAS_ORG
(
  ID        BIGINT PRIMARY KEY AUTO_INCREMENT,
  GRADE     INT,
  NAME      VARCHAR(200),
  ORG_TYPE  SMALLINT,
  ORG_ID     BIGINT,
  STATUS    SMALLINT,
  PARENT_ORG_ID BIGINT,
  COMMENT      VARCHAR(250),
  MODIFY_TIME      DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE(ORG_TYPE,ORG_ID)
);

drop table IF EXISTS UAS_ROLE;
create table UAS_ROLE
(
  ID       BIGINT PRIMARY KEY AUTO_INCREMENT,
  ROLE_NAME VARCHAR(100),
  ROLE_CODE VARCHAR(100),
  STATUS    SMALLINT,
  PARENT_ID     BIGINT,
  ORG_ID        BIGINT,
  ORG_TYPE      SMALLINT,
  ROLE_TYPE_ID  INT,
  COMMENT       VARCHAR(250),
  MODIFY_TIME   DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

drop table IF EXISTS UAS_USER_ROLE;
create table UAS_USER_ROLE
(
  ID        BIGINT PRIMARY KEY AUTO_INCREMENT,
  USER_ID   BIGINT,
  ROLE_ID   BIGINT,
  UNIQUE   (ROLE_ID,USER_ID)
);

drop table IF EXISTS UAS_ROLE_APP;
create table UAS_ROLE_APP
(
  ID        BIGINT PRIMARY KEY AUTO_INCREMENT,
  ROLE_ID   BIGINT,
  APP_ID    BIGINT,
  UNIQUE   (ROLE_ID,APP_ID)
);

drop table IF EXISTS UAS_ROLE_TYPE;
create table UAS_ROLE_TYPE
(
  ID       BIGINT PRIMARY KEY AUTO_INCREMENT,
  NAME     VARCHAR(50) not null,
  COMMENT     VARCHAR(250)
);

drop table IF EXISTS UAS_SESSION;
create table UAS_SESSION
(
  ID       BIGINT PRIMARY KEY AUTO_INCREMENT,
  SESSION_ID  VARCHAR(100) NOT NULL,
  TICKET    VARCHAR(100) NOT NULL,
  USER_ID   BIGINT NOT NULL,
  JOB_NUMBER VARCHAR(50),
  UNIQUE (SESSION_ID, USER_ID)
);

drop table IF EXISTS UAS_LOG;
create table UAS_LOG
(
  ID       BIGINT PRIMARY KEY AUTO_INCREMENT,
  OPER_TIME       DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  OPER_COMMENT     VARCHAR(100),
  LOGIN_IP      VARCHAR(50),
  USER_ID           INT,
  ACCOUNT_ID        VARCHAR(50),
  ACCOUNT_TYPE      SMALLINT,
  APP_ID            BIGINT,
  LAST_LOGIN_MAC     VARCHAR(50),
  LAST_LOGIN_INFO    VARCHAR(50),
  LAST_LOGIN_IP      VARCHAR(50)
);

drop table IF EXISTS UAS_IP_LIST;
create table UAS_IP_LIST
(
  ID       BIGINT PRIMARY KEY AUTO_INCREMENT,
  IP       VARCHAR(50),
  STATUS   SMALLINT default 1 not null,
  UNIQUE (IP)
);

drop table IF EXISTS UAS_ORG_ID_MAP;
create table UAS_ORG_ID_MAP
(
  ID      BIGINT PRIMARY  KEY AUTO_INCREMENT,
  SROUCE_ORG_ID       BIGINT NOT NULL,
  SROUCE_ORG_TYPE     SMALLINT NOT NULL,
  TARGET_ORG_ID       BIGINT NOT NULL,
  TARGET_ORG_TYPE     SMALLINT NOT NULL,
  UNIQUE(SROUCE_ORG_ID,SROUCE_ORG_TYPE,TARGET_ORG_ID,TARGET_ORG_TYPE)
);

drop table IF EXISTS UAS_ORG_TYPE;
create table UAS_ORG_TYPE
(
  ID      SMALLINT PRIMARY  KEY AUTO_INCREMENT,
  COMMENT    VARCHAR(250)
);

---------------------------------------------------------------------------------------
-- 构造获取某个父节点下面的所有子节点的函数
DROP FUNCTION IF EXISTS getChildList;
CREATE FUNCTION getChildList(roleId INT)
RETURNS VARCHAR(4000)
BEGIN
DECLARE sTemp  VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);

SET sTemp ='$';
SET sTempChd = CAST(roleId AS CHAR);

WHILE sTempChd IS NOT NULL DO
SET sTemp= CONCAT(sTemp,',',sTempChd);
SELECT GROUP_CONCAT(id) INTO sTempChd FROM uas_role WHERE FIND_IN_SET(PARENT_ID,sTempChd)>0;
END WHILE;
RETURN sTemp;
END;

---------------------------------------------------------------------------------------
-- 构造获取子节点上的所有父节点函数
DROP FUNCTION IF EXISTS getParentList;
CREATE FUNCTION `getParentList`(rootId INT)
RETURNS varchar(4000)
BEGIN
    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempPar VARCHAR(4000);
    SET sTemp = '';
    SET sTempPar =rootId;

    WHILE sTempPar is not null DO
        IF sTemp != '' THEN
            SET sTemp = concat(sTemp,',',sTempPar);
        ELSE
            SET sTemp = sTempPar;
        END IF;
        SET sTemp = concat(sTemp,',',sTempPar);
        SELECT group_concat(parent_id) INTO sTempPar FROM uas_role where parent_id<>id and FIND_IN_SET(id,sTempPar)>0;
    END WHILE;
RETURN sTemp;
END;

---------------------------------------------------------------------------------------
-- 初始化数据
INSERT INTO UAS_APP(NAME,SECRET,ORG_TYPE,PATH,COMMENT) VALUE ('统一认证系统','sso.mszq.com',1,'','');
INSERT INTO UAS_ROLE_TYPE(NAME,COMMENT) VALUE ('公司岗位','公司岗位');
INSERT INTO UAS_ROLE(ROLE_NAME, STATUS, ROLE_TYPE_ID, COMMENT) SELECT '员工',0, ID,'' FROM UAS_ROLE_TYPE WHERE NAME='公司岗位' LIMIT 1,1;

