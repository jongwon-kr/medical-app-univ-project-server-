package com.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;
public class healthInfo {

	final static String DB_URL = "jdbc:oracle:thin:@capstonedb_medium?TNS_ADMIN=C:/wallet/Wallet_capstoneDB";

	final static String DB_USER = "admin";
	final static String DB_PASSWORD = "Rheodml123!!";

	String data;
	Connection conn = null;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	String cut="㉾";
	String line="㉿";
	
	
	private static healthInfo instance = new healthInfo();
    String returns = "a";
	
	public healthInfo(){
		
	}
	
	 public static healthInfo getInstance() {
	        return instance;
	 }
	
	public void start() throws SQLException {
		
		Properties info = new Properties();
		info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
		info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
		info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, 20);

		OracleDataSource ods = new OracleDataSource();
		ods.setURL(DB_URL);
		ods.setConnectionProperties(info);

		// With AutoCloseable, the connection is closed automatically.
		try (OracleConnection connection = (OracleConnection) ods.getConnection()) {
			// Get the JDBC driver name and version
			DatabaseMetaData dbmd = connection.getMetaData();
			System.out.println("Driver Name: " + dbmd.getDriverName());
			System.out.println("Driver Version: " + dbmd.getDriverVersion());
			// Print some connection properties
			System.out.println("Default Row Prefetch Value is: " + connection.getDefaultRowPrefetch());
			System.out.println("Database Username is: " + connection.getUserName());
			System.out.println();
			// Perform a database operation
		}
	}
	
	
	/*
	 * Displays first_name and last_name from the employees table.
	 */
	 public String connectionDB(String part) {
		 data="";
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

	            String sql = "SELECT 질환명,정의,원인,증상,진단,치료,경과,주의사항 FROM 질환 WHERE 질환명 LIKE ?";

	            pstmt = conn.prepareStatement(sql);	
	            pstmt.setString(1, "%" + part + "%");
	            
	            ResultSet rs = pstmt.executeQuery();
	            while(rs.next()) {
	            	data+=rs.getString(1)+cut+rs.getString(2)+cut+rs.getString(3)+cut+rs.getString(4)+cut+rs.getString(5)+cut+rs.getString(6)+cut+rs.getString(7)+cut+rs.getString(8) + line;
//	            	data[0] = rs.getString(1);
//	            	data[1] = rs.getString(2);
//	            	data[2] = rs.getString(3);
//	            	data[3] = rs.getString(4);
//	            	data[4] = rs.getString(5);
//	            	data[5] = rs.getString(6);
//	            	data[6] = rs.getString(7);
//	            	data[7] = rs.getString(8);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
	            if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	            if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	        }
	        return data;
	    }
}
