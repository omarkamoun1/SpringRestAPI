CREATE SEQUENCE TWEBHOOK_LOG_SEQ ;

CREATE TABLE TWEBHOOK_LOG (
    ID number(10) NOT NULL,
    TEAMID number(10) NOT NULL,
	SYNCTYPE VARCHAR(250)  DEFAULT NULL,
	OPERATIONTYPE number(10)  DEFAULT NULL,
	DATAID VARCHAR(250)  DEFAULT NULL,
	JSON clob   DEFAULT NULL,
	COUNTER number(10)  DEFAULT NULL,
	EXECTIME number(10)  DEFAULT NULL,
	WHREQUESTTIME number(10)  DEFAULT NULL,
	INPROGRESS  char check (INPROGRESS in (0,1)) , 
	STATUSID   number(10)  DEFAULT NULL,
	ERRORMESSAGE clob   DEFAULT NULL,
	DATECREATED  DATE DEFAULT NULL ,
	DATEUPDATED  DATE DEFAULT NULL 
 );
 
CREATE INDEX TWEBHOOK_LOG_INDEX1 ON TWEBHOOK_LOG (ID, TEAMID);
CREATE INDEX TWEBHOOK_LOG_INDEX2 ON TWEBHOOK_LOG (INPROGRESS, STATUSID);