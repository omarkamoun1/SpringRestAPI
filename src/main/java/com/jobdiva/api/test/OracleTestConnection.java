package com.jobdiva.api.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleTestConnection {
	
	public static void main(String args[]) {
		// System.out.println( Encryption.hashStringWithSalt("n3wPassw0rd1",
		// "33+8GvhN6T9SFKJTYLxpvD4m40r7wUDZ+GOD8oZLBDQ=") );
		// System.out.println("+EFz2YXRO9TNk7jTYrlONdvYMCepVGXpqbhAy/+OftE=");
		try {
			// step1 load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// step2 create the connection object
			Connection con = DriverManager.getConnection("jdbc:oracle:oci8:@intdb.com", "intdb", "intdb");
			// step3 create the statement object
			Statement stmt = con.createStatement();
			// step4 execute query
			ResultSet rs = stmt.executeQuery("select 1 from dual");
			while (rs.next())
				System.out.println(rs.getInt(1));
			// step5 close the connection object
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
