package homework.homework.daoImpl;

import java.sql.Connection;
import java.sql.Statement;

public class AbstractDao {
	private Connection conn;
	

	Statement getStatement() throws Exception {
		
		return conn.createStatement();
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}
