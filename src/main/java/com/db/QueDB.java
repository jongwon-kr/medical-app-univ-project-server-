package com.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class QueDB {
    final static String DB_URL = "jdbc:oracle:thin:@capstonedb_medium?TNS_ADMIN=C:/wallet/Wallet_capstoneDB";
    final static String DB_USER = "admin";
    final static String DB_PASSWORD = "Rheodml123!!";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    PreparedStatement pstmt;
    PreparedStatement pstmt2;
    int incrementedValue = 0;
    int currentValue = 0;
    String cut = "§";

    private static QueDB instance = new QueDB();
    String returns = "a";

    public QueDB() {
    	
    }

    public static QueDB getInstance() {
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
    public String connectionDB(String name, String result) {
    	String ids_res = "";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //java.util.Date currentDate = new java.util.Date();
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());            
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql1 = "SELECT 순번 FROM 문진 ORDER BY 순번 DESC";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql1);

            if (rs.next()) {
                currentValue = rs.getInt("순번");
            }
            incrementedValue = currentValue + 1;
            
            System.out.println("incrementedValue = "+incrementedValue);
            
            String sql2 = "INSERT INTO 문진 VALUES(?,?,?,?)";
            pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1, incrementedValue);
            pstmt2.setDate(2, sqlDate);
            pstmt2.setString(3, name);
            pstmt2.setString(4, result);
            pstmt2.executeUpdate();

            String[] resArr = result.split(","); // 결과를 증상별로 분할하여 배열에 저장
            List<String> idsList = new ArrayList<>(); // 질환명을 저장할 리스트

            if (resArr.length >= 3) {
                for (int i = 0; i < resArr.length - 2; i++) {
                    for (int j = i + 1; j < resArr.length - 1; j++) {
                        for (int k = j + 1; k < resArr.length; k++) {
                            String symptom1 = resArr[i].trim();
                            String symptom2 = resArr[j].trim();
                            String symptom3 = resArr[k].trim();

                            String sql3 = "SELECT 질환명 FROM 질환 WHERE 증상 LIKE ? AND 증상 LIKE ? AND 증상 LIKE ?";
                            // 질환테이블에서 3개의 증상에 해당하는 질환명을 선택하는 SQL 쿼리
                            pstmt = conn.prepareStatement(sql3);
                            pstmt.setString(1, "%" + symptom1 + "%");
                            pstmt.setString(2, "%" + symptom2 + "%");
                            pstmt.setString(3, "%" + symptom3 + "%");
                            rs = pstmt.executeQuery(); // 쿼리 실행

                            while (rs.next()) {
                                String disease = rs.getString("질환명");
                                idsList.add(disease); // 3개의 증상에 해당하는 질환명을 리스트에 추가
                            }

                            rs.close();
                            pstmt.close();
                        }
                    }
                }
            }

            // 중복 데이터 카운트
            Map<String, Integer> countMap = new LinkedHashMap<>();
            for (String disease : idsList) {
                countMap.put(disease, countMap.getOrDefault(disease, 0) + 1);
            }

            // 카운트 값을 기준으로 내림차순 정렬
            List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(countMap.entrySet());
            Collections.sort(sortedList, Collections.reverseOrder(Map.Entry.comparingByValue()));

            // 결과 출력
            for (Map.Entry<String, Integer> entry : sortedList) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
           
            int count = 0;
            for (Map.Entry<String, Integer> entry : sortedList) {
                if (count <5) {
                    ids_res += entry.getKey() + cut;
                    count++;
                } else {
                    break;
                }
            }
            

            
            String sql = "SELECT 이름 FROM 문진정보 where 이름 = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                sql = "UPDATE 문진정보 SET 의심질병 = ? WHERE 이름 = ?";
                pstmt2 = conn.prepareStatement(sql);
                pstmt2.setString(1, ids_res);
                pstmt2.setString(2, name);
                pstmt2.executeUpdate();              
            }
            else {
		        sql = "INSERT INTO 문진정보 VALUES(?,?)";
		        pstmt2 = conn.prepareStatement(sql);
		        pstmt2.setString(1, name);
		        pstmt2.setString(2, ids_res);
		        pstmt2.executeUpdate();
            }

      
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
        return ids_res;
    }

}
