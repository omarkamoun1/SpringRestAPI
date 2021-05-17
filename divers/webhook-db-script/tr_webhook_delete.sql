create or replace TRIGGER tr_webhook_de_tcand
after delete
    on tcandidate
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :old.teamid;
    syncType := 'candidate';
    operation := 3;
    api_id := to_char(:old.id);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/


create or replace TRIGGER tr_webhook_de_tcust
after delete
    on tcandidate
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :old.teamid;
    syncType := 'contact';
    operation := 3;
    api_id := to_char(:old.id);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/

create or replace TRIGGER tr_webhook_de_tcustcomp
after delete
    on tcustomercompany
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :old.teamid;
    syncType := 'company';
    operation := 3;
    api_id := to_char(:old.id);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/

create or replace TRIGGER tr_webhook_de_temp_wed
after delete
    on TEMPLOYEE_WED
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :old.recruiter_teamid;
    syncType := 'timesheet';
    operation := 3;
    api_id := to_char(:old.timecardid);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/


create or replace TRIGGER tr_webhook_de_trfq
after delete
    on tcandidate
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :old.teamid;
    syncType := 'job';
    operation := 3;
    api_id := to_char(:old.id);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/

create or replace TRIGGER tr_wh_delete_tcand_cat
after delete
    on tcandidate_category
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin
    api_teamid := :old.teamid;
    syncType := 'candidate';
    operation := 2;
    api_id := to_char(:old.candidateid);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/


create or replace TRIGGER tr_webhook_del_billing
after delete
    on TEMPLOYEE_BILLINGRECORD
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin                  
    api_teamid := :old.recruiter_teamid;
    syncType := 'billing';
    operation := 3;
    api_id := to_char(:old.employeeid) || ',' || to_char(:old.recid);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/

create or replace TRIGGER tr_webhook_del_salary
after delete
    on TEMPLOYEE_SALARYRECORD
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin                  
    api_teamid := :old.recruiter_teamid;
    syncType := 'salary';
    operation := 3;
    api_id := to_char(:old.employeeid) || ',' || to_char(:old.recid);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/

create or replace TRIGGER tr_webhook_del_texp_entry
after delete
    on TEXPENSE_ENTRY
    for each row
Declare 
    api_teamid number;
    syncType varchar2(4000);
    operation number;
    api_id varchar2(4000);
Begin                  
    api_teamid := :old.recruiter_teamid;
    syncType := 'expense';
    operation := 3;
    api_id := to_char(:old.ENTRYID);
    webhook_event(api_teamid,syncType,operation,api_id);
EXCEPTION
    WHEN OTHERS THEN
        NULL;
END;
/