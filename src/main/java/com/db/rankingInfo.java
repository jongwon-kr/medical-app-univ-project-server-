package com.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class rankingInfo {

	final static String DB_URL = "jdbc:oracle:thin:@capstonedb_medium?TNS_ADMIN=C:/wallet/Wallet_capstoneDB";

	final static String DB_USER = "admin";
	final static String DB_PASSWORD = "Rheodml123!!";
	Connection conn = null;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	String cut="㉾";
	
	private static rankingInfo instance = new rankingInfo();
    String returns = "a";
	
	public rankingInfo(){
		
	}
	
	 public static rankingInfo getInstance() {
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
	 public String connectionDB() {
		 int dNum[] = new int[10];
		 int i;
		 String data = "";
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

	            String sql = "SELECT 질환번호,빈도수 FROM (SELECT 질환번호,빈도수, DENSE_RANK() OVER(ORDER BY 빈도수) as rnk from 빈도) where ROWNUM<=10 ORDER BY 빈도수 DESC";
	            pstmt = conn.prepareStatement(sql);	
	            ResultSet rs = pstmt.executeQuery();
	            i=0;
	            while(rs.next()) {
	            	dNum[i++] = rs.getInt(1);
	            }
	            
	            String sql2 = "SELECT 질환명 FROM 질환 WHERE 질환번호 = ?";
	            pstmt = conn.prepareStatement(sql2);
	            for(int j=0;j<i;j++) {
	            	pstmt.setInt(1, dNum[j]);
	            	rs = pstmt.executeQuery();
	            	if(rs.next()) {
	            		data += rs.getString(1) + cut;
	            	}
	            }
	            
	            returns = data;
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (pstmt2 != null)try {pstmt2.close();    } catch (SQLException ex) {}
	            if (pstmt != null)try {pstmt.close();} catch (SQLException ex) {}
	            if (conn != null)try {conn.close();    } catch (SQLException ex) {    }
	        }
	        return returns;
	    }
	 
}
