-- #127 Ordering of validation stamps
ALTER TABLE VALIDATION_STAMP ADD COLUMN ORDERNB INTEGER NULL;
-- Uses the natural order for initialization
UPDATE VALIDATION_STAMP R SET ORDERNB  = (SELECT COUNT(*) FROM VALIDATION_STAMP RS WHERE RS.BRANCH = R.BRANCH AND RS.NAME <= R.NAME);
-- Sets this column as required
ALTER TABLE VALIDATION_STAMP ALTER COLUMN ORDERNB SET NOT NULL;

-- @rollback

ALTER TABLE VALIDATION_STAMP DROP COLUMN ORDERNB;-- @mysql
-- See update 26
