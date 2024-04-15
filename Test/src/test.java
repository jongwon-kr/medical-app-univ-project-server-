
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class test {
	
	Connection con = null;
	String url = "jdbc:oracle:thin:@capstonedb_medium?TNS_ADMIN=C:/wallet/Wallet_capstoneDB"; String id = "admin"; String password = "Rheodml123!!";
	public test() {
		try { Class.forName("oracle.jdbc.driver.OracleDriver");
		System.out.println("드라이버 적재 성공");
		} catch (ClassNotFoundException e) { System.out.println("No Driver."); } 
	}
	private void DB_Connect() {
		try { con = DriverManager.getConnection(url, id, password);
		System.out.println("DB 연결 성공");
		} catch (SQLException e) { System.out.println("Connection Fail"); }
	}
	private void sqlRun() throws SQLException{ // 단순 검색
	String query = "select ID,PW from 사용자";
	try { DB_Connect();
	Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query);
	while (rs.next()) {
		System.out.print(rs.getString("ID")+"\t"+rs.getString("PW"));
	}
	stmt.close(); rs.close();
	} catch (SQLException e) { e.printStackTrace(); 
	} finally { con.close(); }
	}
	public static void main(String arg[]) throws SQLException {
		test dbconquery = new test();
		dbconquery.sqlRun();
	}
}