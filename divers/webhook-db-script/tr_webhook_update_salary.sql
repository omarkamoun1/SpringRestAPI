create or replace TRIGGER tr_webhook_update_salary
after update
    on TEMPLOYEE_SALARYRECORD
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
	IF nvl(:new.APPROVED,0) = 1 THEN
		api_teamid := :new.recruiter_teamid;
		syncType := 'salary';
		operation := 2;
		api_id := to_char(:new.employeeid) || ',' || to_char(:new.recid);
		webhook_event(api_teamid,syncType,operation,api_id);
	END IF;
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;

