create or replace TRIGGER tr_webhook_update_temp_wed
after update
    on TEMPLOYEE_WED
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :new.recruiter_teamid;
    syncType := 'timesheet';
    operation := 2;
    api_id := to_char(:new.timecardid);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
