/*
===================增量语句模板========================

1、新建xxx_add.sql文件的人，首先根据自身所写的增量语句所在的库，新建对应的sql文件
2、然后按相应格式将相应的增量语句写进库模块中。

===============这是模块格式如下：=================
*/
begin by fangen
ALTER TABLE auth_account ADD UNIQUE `UQ_MERCHANT_NUM` (merchant_num);
end by fangen
