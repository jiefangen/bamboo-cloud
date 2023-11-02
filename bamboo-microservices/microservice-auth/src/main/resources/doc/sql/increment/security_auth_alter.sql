/*
===================增量语句模板========================

1、新建xxx_add.sql文件的人，首先根据自身所写的增量语句所在的库，新建对应的sql文件
2、然后按相应格式将相应的增量语句写进库模块中。

===============这是模块格式如下：=================
*/
begin by fangen
ALTER TABLE auth_account ADD UNIQUE `UQ_MERCHANT_NUM` (merchant_num);
ALTER TABLE auth_permission MODIFY permission_code VARCHAR(100);

ALTER TABLE auth_account ADD `credentials` VARCHAR(150) COMMENT '账户凭证' AFTER `password`;
ALTER TABLE auth_account ADD `secret_key` VARCHAR(100) NOT NULL COMMENT '密钥' AFTER `password`;
ALTER TABLE auth_account MODIFY credentials VARCHAR(300) COMMENT '账户凭证';
end by fangen
