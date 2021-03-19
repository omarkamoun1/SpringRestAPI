create or replace procedure webhook_event
(teamid in number
,syncType in varchar2
,operation in number
,id in varchar2)
is
  req utl_http.req;
  has_webhook_api number;
  res utl_http.resp;
  url varchar2(4000) := 'http://api01:88/api/webhook/syncWebhook';
  name varchar2(4000);
  buffer varchar2(4000); 
  params varchar2(4000) :='teamId='||teamid||'&'||'syncType='||syncType||'&'||'operation='||operation||'&'||'id='||id;
 
begin
  INSERT INTO TWEBHOOK_FAILEDREQUEST (ID, TEAMID, SYNCTYPE, OPERATIONTYPE, DATAID, DATECREATED ) values (TWEBHOOK_FAILEDREQUEST_SEQ.nextval, 1, 'update', 1, 1, sysdate);
  
  select count(*) into has_webhook_api from TWEBHOOK_CONFIGURATION where TEAMID = teamid and ACTIVE = 1 and rownum=1;
  if has_webhook_api is not null and has_webhook_api >0 then
    begin
      req := utl_http.begin_request(url, 'POST',' HTTP/1.1');
      utl_http.set_header(req, 'Content-Length', length(params));
      utl_http.set_header(req, 'Content-Type', 'application/x-www-form-urlencoded');
      utl_http.write_text (req, params);
      utl_http.set_transfer_timeout(5);
      res := utl_http.get_response(req);
      loop
        utl_http.read_line(res, buffer);
        -- dbms_output.put_line(buffer);
      end loop;
      utl_http.end_response(res);
    exception
      when utl_http.end_of_body 
      then
        utl_http.end_response(res);
    end;
  end if;
end webhook_event;