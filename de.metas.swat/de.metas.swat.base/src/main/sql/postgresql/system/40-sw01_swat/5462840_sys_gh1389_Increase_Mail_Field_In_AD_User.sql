-- 2017-05-17T11:53:05.333
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=200,Updated=TO_TIMESTAMP('2017-05-17 11:53:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=5396
;

commit;
-- 2017-05-17T11:53:08.090
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_user','EMail','VARCHAR(200)',null,null)
;

commit;

-- 2017-05-17T11:54:24.850
-- URL zum Konzept
UPDATE AD_Field SET ColumnDisplayLength=200, DisplayLength=200,Updated=TO_TIMESTAMP('2017-05-17 11:54:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=7017
;

