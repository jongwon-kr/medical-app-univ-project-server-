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

public class durationInfo {

	final static String DB_URL = "jdbc:oracle:thin:@capstonedb_medium?TNS_ADMIN=C:/wallet/Wallet_capstoneDB";

	final static String DB_USER = "admin";
	final static String DB_PASSWORD = "Rheodml123!!";
	Connection conn = null;
	PreparedStatement pstmt;
	PreparedStatement pstmt2;
	
	private static durationInfo instance = new durationInfo();
    String returns = "a";
	
	public durationInfo(){
		
	}
	
	 public static durationInfo getInstance() {
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
	 public String connectionDB(String dName) {
		 int dNum = 0;
	        try {
	            Class.forName("oracle.jdbc.driver.OracleDriver");
	            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

	            String sql = "SELECT 질환번호 FROM 질환 WHERE 질환명 = ?";
	            pstmt = conn.prepareStatement(sql);	
	            pstmt.setString(1, dName);
	            

	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) { //질환명에 해당하는 질환번호를 찾아서 dNum에 저장
	                dNum = rs.getInt(1);
	            }
	            System.out.println("dNum="+dNum);
	            String search = "SELECT 질환번호 FROM 빈도 WHERE 질환번호 = ?"; //질환번호가 이미 빈도 테이블에 저장된 질환번호인지 확인
	            pstmt2 = conn.prepareStatement(search);
	            pstmt2.setInt(1, dNum);
	            pstmt2.executeUpdate();
	            rs = pstmt2.executeQuery();
	            
	            if(rs.next()) { //존재한다면 값을 1올리는 업데이트 쿼리문 사용
		            String sql2 = "update 빈도 set 빈도수 = 빈도수+1 where 질환번호 = ?";
	                pstmt2 = conn.prepareStatement(sql2);
	                pstmt2.setInt(1, dNum);
	                pstmt2.executeUpdate();
	            }
	            else { //존재하지않는다면 새로삽입 초기값은 1
	            	String sql2 = "insert into 빈도 values(?,1)";
	                pstmt2 = conn.prepareStatement(sql2);
	                pstmt2.setInt(1, dNum);
	                pstmt2.executeUpdate();
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
