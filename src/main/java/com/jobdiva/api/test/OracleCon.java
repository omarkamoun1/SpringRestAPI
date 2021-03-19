/**
* 
*/
package com.jobdiva.api.test;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;

class OracleCon {
	
	public static void main(String args[]) {
		try {
			DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
			dataSourceBuilder.driverClassName("oracle.jdbc.OracleDriver");
			dataSourceBuilder.url("jdbc:oracle:thin:@10.50.139.125:1521:intdb.intdb12c");
			dataSourceBuilder.username("intdb");
			dataSourceBuilder.password("intdb");
			//
			//
			DataSource dataSource = dataSourceBuilder.build();
			Connection con = dataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement stmt = con.prepareStatement("insert into test1(a1,a2) values( ?, ?) ");
			stmt.setString(1, "1");
			stmt.setString(2, "2");
			stmt.executeUpdate();
//			con.rollback();
			con.close();
			//
			//
			//
			// step1 load the driver class
			// Class.forName("oracle.jdbc.OracleDriver");
			// // step2 create the connection object
			// con =
			// DriverManager.getConnection("jdbc:oracle:thin:@10.50.139.125:1521:intdb.intdb12c",
			// "intdb", "intdb");
			// con.setAutoCommit(false);
			// // step3 create the statement object
			// stmt = con.prepareStatement("insert into test1(a1,a2) values( ?,
			// ?) ");
			// stmt.setString(1, "1");
			// stmt.setString(2, "2");
			// stmt.executeUpdate();
			// con.rollback();
			// con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}