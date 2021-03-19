create or replace TRIGGER tr_webhook_update_tcustomercompany
after update
    on tcustomercompany
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :new.teamid;
    syncType := 'company';
    operation := 2;
    api_id := to_char(:new.id);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;

