create or replace TRIGGER tr_webhook_update_texp_entry
after update
    on TEXPENSE_ENTRY
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin                  
    api_teamid := :new.recruiter_teamid;
    syncType := 'expense';
    operation := 2;
    api_id := to_char(:new.ENTRYID);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
