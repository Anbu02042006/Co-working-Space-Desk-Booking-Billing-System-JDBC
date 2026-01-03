package com.cowork.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBUtil {
	public static Connection getDBConnection() throws Exception{
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","anbu");
		return conn;
		}
		catch(SQLException e) {
			System.out.println(e);
		}
		return null;
	}
}
