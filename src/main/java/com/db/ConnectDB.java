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

public class ConnectDB {

	final static String DB_URL = "jdbc:oracle:thin:@capstonedb_medium?TNS_ADMIN=C:/wallet/Wallet_capstoneDB";

	final static String DB_USER = "admin";
	final static String DB_PASSWORD = "Rheodml123!!";
	Connection conn = null;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	
	private static ConnectDB instance = new ConnectDB();
    String returns = "a";
	
	public ConnectDB(){
		
	}
	
	 public static ConnectDB getInstance() {
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
	 public String connectionDB(String id, String pw, String nick, String spn, String ans) {
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

	            String sql = "SELECT id FROM 사용자 WHERE id = ?";
	            pstmt = conn.prepareStatement(sql);	
	            pstmt.setString(1, id);
	            

	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                returns = "이미 존재하는 아이디 입니다.";
	            }
	            else {
	                String sql2 = "INSERT INTO 사용자 VALUES(?,?,?,?,?)";
	                pstmt2 = conn.prepareStatement(sql2);
	                pstmt2.setString(1, id);
	                pstmt2.setString(2, pw);
	                pstmt2.setString(3, nick);
	                pstmt2.setString(4, spn);
	                pstmt2.setString(5, ans);
	                pstmt2.executeUpdate();
	                returns = "회원 가입 성공 !";
	            }
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
