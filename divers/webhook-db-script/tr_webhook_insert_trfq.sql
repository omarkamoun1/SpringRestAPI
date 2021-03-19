create or replace TRIGGER tr_webhook_insert_trfq
after insert
    on trfq
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :new.teamid;
    syncType := 'job';
    operation := 1;
    api_id := to_char(:new.id);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
