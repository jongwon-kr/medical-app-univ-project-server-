package com.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class myinfo {

    final static String DB_URL = "jdbc:oracle:thin:@capstonedb_medium?TNS_ADMIN=C:/wallet/Wallet_capstoneDB";
    final static String DB_USER = "admin";
    final static String DB_PASSWORD = "Rheodml123!!";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt;
    PreparedStatement pstmt2;


    private static myinfo instance = new myinfo();
    String returns = "a";

    public myinfo() {

    }

    public static myinfo getInstance() {
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
    public String connectionDB(String nick) {
    	String result = null;
    	String ID = null;
    	int que_count = 0; 
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            java.util.Date currentDate = new java.util.Date();
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());            
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT ID FROM 사용자 WHERE NICKNAME = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nick);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ID = rs.getString("ID");
            }
            String sql1 = "SELECT COUNT(*) FROM 문진 GROUP BY 이름";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql1);
            if (rs.next()) {
                que_count = rs.getInt(1);
            }
            
            result = ID + "," + Integer.toString(que_count);


            rs.close();
            conn.close();
            stmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt2 != null) try { pstmt2.close(); } catch (SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) {}
            if (conn != null) try { conn.close(); } catch (SQLException ex) {}
        }
        return result;
    }
}
