package com.jobdiva.api.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.lang.Nullable;

public class JobDivaSqlLobValue extends SqlLobValue {
	
	public JobDivaSqlLobValue(byte[] bytes) {
		super(bytes);
	}
	
	public JobDivaSqlLobValue(@Nullable byte[] bytes, LobHandler lobHandler) {
		super(bytes, lobHandler);
	}
	
	public JobDivaSqlLobValue(@Nullable String content, LobHandler lobHandler) {
		super(content, lobHandler);
	}
	
	@Override
	public void setTypeValue(PreparedStatement ps, int paramIndex, int sqlType, @Nullable String typeName) throws SQLException {
		super.setTypeValue(ps, paramIndex, Types.CLOB, typeName);
	}
}
