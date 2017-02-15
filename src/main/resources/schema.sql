DROP DATABASE IF EXISTS simconf;

CREATE DATABASE simconf
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE simconf;

-- ----------------------------
--  Table structure for user
-- ----------------------------
DROP TABLE
IF EXISTS user;

CREATE TABLE user
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  username     VARCHAR(20)                           NOT NULL
  COMMENT '用户名',
  password     VARCHAR(64)                           NOT NULL
  COMMENT '密码',
  salt         VARCHAR(64)                           NOT NULL
  COMMENT '密码盐',
  fullname     VARCHAR(32)                           NOT NULL
  COMMENT '姓名',
  is_deleted   TINYINT                               NOT NULL                    DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '用户表';
CREATE UNIQUE INDEX id_UNIQUE
  ON user (id);
CREATE INDEX create_ix
  ON user (created_time);
CREATE UNIQUE INDEX username_UNIQUE
  ON user (username);

-- ----------------------------
--  Table structure for role
-- ----------------------------
DROP TABLE
IF EXISTS role;

CREATE TABLE role
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '角色代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '角色名称',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '角色表';
CREATE UNIQUE INDEX id_UNIQUE
  ON role (id);
CREATE INDEX create_ix
  ON role (created_time);
CREATE UNIQUE INDEX code_UNIQUE
  ON role (code);

-- ----------------------------
--  Table structure for menu
-- ----------------------------
DROP TABLE
IF EXISTS menu;

CREATE TABLE menu
(
  id           BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code         VARCHAR(32)                           NOT NULL
  COMMENT '菜单代码',
  name         VARCHAR(32)                           NOT NULL
  COMMENT '菜单名称',
  pcode        VARCHAR(32)                           NOT NULL                DEFAULT ''
  COMMENT '父菜单代码',
  url          VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单地址',
  sort         INT(11)                               NOT NULL                DEFAULT 0
  COMMENT '菜单排序(从0开始)',
  icon         VARCHAR(128)                          NOT NULL                DEFAULT ''
  COMMENT '菜单图标的样式',
  is_deleted   TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '菜单表';
CREATE UNIQUE INDEX id_UNIQUE
  ON menu (id);
CREATE INDEX create_ix
  ON menu (created_time);
CREATE INDEX sort_ix
  ON menu (sort);
CREATE UNIQUE INDEX code_UNIQUE
  ON menu (code);

-- ----------------------------
--  Table structure for user_role
-- ----------------------------
DROP TABLE
IF EXISTS user_role;

CREATE TABLE user_role
(
  username  VARCHAR(20) NOT NULL
  COMMENT '用户名',
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  PRIMARY KEY (username, role_code)
)
  COMMENT '用户角色表';

-- ----------------------------
--  Table structure for role_menu
-- ----------------------------
DROP TABLE
IF EXISTS role_menu;

CREATE TABLE role_menu
(
  role_code VARCHAR(32) NOT NULL
  COMMENT '角色代码',
  menu_code VARCHAR(32) NOT NULL
  COMMENT '菜单代码',
  PRIMARY KEY (role_code, menu_code)
)
  COMMENT '角色菜单表';

-- ----------------------------
--  Table structure for project
-- ----------------------------
DROP TABLE
IF EXISTS project;

CREATE TABLE project
(
  id              BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  code            VARCHAR(32)                           NOT NULL
  COMMENT '项目代码',
  name            VARCHAR(64)                           NOT NULL
  COMMENT '项目名称',
  push_url        VARCHAR(256)                          NOT NULL                DEFAULT ''
  COMMENT '推送地址',
  create_username VARCHAR(20)                           NOT NULL
  COMMENT '项目负责人',
  is_deleted      TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time    TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time    TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '项目表';
CREATE UNIQUE INDEX id_UNIQUE
  ON project (id);
CREATE UNIQUE INDEX code_UNIQUE
  ON project (code);
CREATE INDEX create_ix
  ON project (created_time);

-- ----------------------------
--  Table structure for conf
-- ----------------------------
DROP TABLE
IF EXISTS conf;

CREATE TABLE conf
(
  id              BIGINT(20) PRIMARY KEY AUTO_INCREMENT NOT NULL
  COMMENT '主键, 自增',
  proj_code       VARCHAR(32)                           NOT NULL
  COMMENT '项目代码',
  env             VARCHAR(32)                           NOT NULL                DEFAULT 'common'
  COMMENT '环境',
  name            VARCHAR(64)                           NOT NULL
  COMMENT '名',
  value           VARCHAR(512)                          NOT NULL
  COMMENT '值',
  description     VARCHAR(512)                          NOT NULL                DEFAULT ''
  COMMENT '描述',
  create_username VARCHAR(20)                           NOT NULL
  COMMENT '创建人',
  is_deleted      TINYINT                               NOT NULL                DEFAULT 0
  COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  created_time    TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  updated_time    TIMESTAMP                             NOT NULL                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  COMMENT '更新时间'
)
  COMMENT '配置表';
CREATE UNIQUE INDEX id_UNIQUE
  ON conf (id);
CREATE INDEX sys_code_ix
  ON conf (proj_code);
CREATE INDEX environment_ix
  ON conf (env);

#====================初始数据====================#

-- ----------------------------
--  data for user
-- ----------------------------
INSERT INTO user
(username, password, salt, fullname)
VALUES
  ('admin', '9606b0029ba4a8c9369f288cced0dc465eb5eabd', '3685072edcf8aad8', '管理员');

-- ----------------------------
--  data for role
-- ----------------------------
INSERT INTO role
(code, name)
VALUES
  ('ROLE_ADMIN', '管理员'),
  ('ROLE_USER', '普通用户');

-- ----------------------------
--  data for menu
-- ----------------------------
INSERT INTO menu
(code, name, pcode, url, sort, icon)
VALUES
  ('DASHBOARD', '工作台', '', 'index', 0, 'menu-icon fa fa-dashboard'),

  ('SYSTEM', '系统', 'DASHBOARD', 'system', 1, 'menu-icon fa fa-cogs'),
  ('SYSTEM_USER', '用户管理', 'SYSTEM', 'system/user', 0, ''),
  ('SYSTEM_ROLE', '角色管理', 'SYSTEM', 'system/role', 1, ''),
  ('SYSTEM_MENU', '菜单管理', 'SYSTEM', 'system/menu', 2, ''),

  ('PASSWORD', '修改密码', 'DASHBOARD', 'password', 2, 'menu-icon fa fa-key'),
  ('PROJECT', '项目配置', 'DASHBOARD', 'project', 3, 'menu-icon fa fa-leaf');

-- ----------------------------
--  data for user_role
-- ----------------------------
INSERT INTO user_role
VALUES
  ('admin', 'ROLE_ADMIN');

-- ----------------------------
--  data for role_menu
-- ----------------------------
INSERT INTO role_menu SELECT
                        'ROLE_ADMIN',
                        code
                      FROM menu;

INSERT INTO role_menu SELECT
                        'ROLE_USER',
                        code
                      FROM menu
                      WHERE code IN ('DASHBOARD', 'PASSWORD', 'PROJECT');
