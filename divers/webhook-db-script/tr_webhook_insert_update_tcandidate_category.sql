create or replace TRIGGER tr_wh_insert_tcand_cat
after insert
    on tcandidate_category
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :new.teamid;
    syncType := 'candidate';
    operation := 1;
    api_id := to_char(:new.candidateid);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/
create or replace TRIGGER tr_wh_update_tcand_cat
after update
    on tcandidate_category
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :new.teamid;
    syncType := 'candidate';
    operation := 2;
    api_id := to_char(:new.candidateid);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
