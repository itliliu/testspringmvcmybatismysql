-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE DEFINER=`weadmin`@`%` PROCEDURE `listDetailInfo`(
    IN uid INT(11),
    IN statusIN INT(11),
    IN clientIDIN INT(11),
    IN keyword VARCHAR(250),
    IN pageIndex INT(11),
    IN pageSize INT(11)
)
BEGIN
DECLARE current_number INT;
SET current_number = (pageIndex -1)*pageSize;
SET @currentNumber = current_number;
SET @pageSize = pageSize;


DROP TEMPORARY TABLE IF EXISTS tmp_Client;
CREATE TEMPORARY TABLE tmp_Client (
      ClientID INT(11) NOT NULL, 
      ClientName VARCHAR(128) NOT NULL,
      ParentID INT(11) DEFAULT NULL
);

SET @roleID = (SELECT RoleID FROM User WHERE UserID=uid);
IF @roleID=1 THEN -- AccountTeam
    IF clientIDIN = -1 THEN
        INSERT INTO tmp_Client(SELECT Client.ClientID,Client.ClientName,Client.ParentID 
                FROM Client LEFT JOIN UserClientMap ucm on Client.ClientID=ucm.ClientID 
                WHERE ucm.UserID=uid);
    ELSE 
        INSERT INTO tmp_Client(SELECT Client.ClientID,Client.ClientName,Client.ParentID 
                FROM Client LEFT JOIN UserClientMap ucm ON Client.ClientID=ucm.ClientID 
                WHERE ucm.UserID=uid 
                AND (Client.ClientID=clientIDIN OR Client.ParentID=clientIDIN));
    END IF;
ELSE
    IF clientIDIN = -1 THEN
        INSERT INTO tmp_Client(SELECT ClientID,ClientName,ParentID 
                FROM Client);
    ELSE 
        INSERT INTO tmp_Client(SELECT ClientID,ClientName,ParentID 
                FROM Client
                WHERE ClientID=clientIDIN OR Client.ParentID=clientIDIN);
    END IF;
END IF;
-- for each project brief, select a  project
SET @_sql = ' FROM project_brief brief 
            LEFT JOIN tmp_Client client on brief.client_id = client.ClientID
             WHERE client.ClientID IS NOT NULL ';

IF clientIDIN <> -1 THEN
    SET @_sql=CONCAT(@_sql,' AND (brief.client_id=',clientIDIN,
            ' OR client.ParentID=',clientIDIN,') ');
END IF;

-- statusIn = -1, get all status
IF statusIN = -1 THEN
    SET @_sql=CONCAT(@_sql,' AND brief.status>=0  ');
ELSE
    SET @_sql=CONCAT(@_sql,' AND brief.status=',statusIN);
END IF;

SET @keyword = IFNULL(keyword,'');
IF LENGTH(keyword) > 0 THEN
    SET @_sql=CONCAT(@_sql,' AND ( brief.brief_id like \'',keyword,'\'',
                ' OR (brief.status=0 AND brief.brief_name like \'%',keyword,'%\')'
                ' OR client.ClientName like \'%',keyword,'%\')');
END IF;

SET @_sql = CONCAT(@_sql,' ORDER BY brief.modify_date DESC');

SET @sql_count = CONCAT(' SELECT COUNT(brief.brief_id) AS total_page_number ',@_sql);

PREPARE stmt FROM @sql_count;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @sql_select=CONCAT(' SELECT brief.brief_id,brief.user_id,brief.client_id,
brief.brief_name,brief.Status,
brief.brief_context,brief.create_date,brief.modify_date,brief.is_star  ',@_sql,' LIMIT ?,?');

PREPARE stmt FROM @sql_select;
EXECUTE stmt USING @currentNumber,@pageSize;
DEALLOCATE PREPARE stmt;

END
