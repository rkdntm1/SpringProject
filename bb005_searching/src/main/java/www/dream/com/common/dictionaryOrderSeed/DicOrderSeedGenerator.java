package www.dream.com.common.dictionaryOrderSeed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DicOrderSeedGenerator {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String USER_ID = "system";
	private static final String PASSWORD = "1234";
	
	private static Connection conn() {
		try {
			// 주어진 문자열을 기준으로 클래스 찾기
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver 찾기 성공");
			Connection connection = DriverManager.getConnection(URL, USER_ID, PASSWORD);
			System.out.println("connection 성공");
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		//62진수의 문자를 준비함
		char[] ch = new char[62];
		int idx = 0;
		for (char i = '0'; i <= '9'; i++) { //10
			ch[idx++] = i;
		}
		for (char i = 'A'; i <= 'Z'; i++) { //26
			ch[idx++] = i;
		}
		for (char i = 'a'; i <= 'z'; i++) { //26
			ch[idx++] = i;
		}		
		
		Connection conn = conn();
		if (conn == null)
			return;
		try {
			//총 약 1500만개 만..
			PreparedStatement stmt = conn.prepareStatement("insert into s_id_seed(seq_id, seed) values(?, ?)");
			long seqId = 0;
			char[] chSeed = new char[5];
			for (int i = 0; i < 1; i++) {
				chSeed[0] = ch[i];
				for (int j = 0; j < 62; j++) {
					chSeed[1] = ch[j];
					for (int k = 0; k < 62; k++) {
						chSeed[2] = ch[k];
						for (int l = 0; l < 62; l++) {
							chSeed[3] = ch[l];
							for (int m = 0; m < 62; m++) {
								chSeed[4] = ch[m];	
								stmt.setLong(1, seqId);
								stmt.setString(2, new String(chSeed));
								stmt.addBatch(); // 쭉쌓았다가 한번에 실행 
								stmt.clearParameters();
								seqId++;
							}
						}
					}
					stmt.executeBatch();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
