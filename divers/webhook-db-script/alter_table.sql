alter table TWEBHOOK_CONFIGURATION add ENABLE_TIMESHEET char check (ENABLE_TIMESHEET in (0,1))  ;
alter table TWEBHOOK_CONFIGURATION add ENABLE_EXPENSE char check (ENABLE_EXPENSE in (0,1)) ;