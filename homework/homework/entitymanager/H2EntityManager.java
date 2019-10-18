package homework.homework.entitymanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.h2.tools.Server;

import homework.homework.config.Config;

public class H2EntityManager implements EntityManager {

	private static final String PORT = "PORT";
	protected static final Logger log = Logger.getLogger("FileEntityManager");

	
	public static void main(String...strings ) throws Exception {
		if("start".equalsIgnoreCase(strings[0])) {
			new H2EntityManager().init();
		}else if("stop".equalsIgnoreCase(strings[0])) {
			new H2EntityManager().shutdownDB();
		}else {
			System.out.println("usage: H2EntityManager stop|start to start the database server");
		}
	}

	public void init() throws Exception {
		Class.forName("org.h2.Driver");
		
		String port = Config.getInstance().getProperty(PORT);
		Server.createTcpServer("-tcpPort", port, "-tcpAllowOthers").start();
		
		Connection conn = DriverManager.
	            getConnection("jdbc:h2:~/test;AUTO_SERVER=TRUE", "sa", "");
		Statement stmt = conn.createStatement();
		
		stmt.executeLargeUpdate("drop table if exists phonebook;"
				+"drop sequence if exists seq;"
				+"CREATE SEQUENCE SEQ;"
				+"create table phonebook(id int primary key, name varchar(255), phone varchar(255) unique, unique key phone (phone) );"
				+"insert into phonebook values (NEXT VALUE FOR SEQ, 'petr', 777458214); "
				+"insert into phonebook values (NEXT VALUE FOR SEQ, 'petr', 777458321); "
				+"insert into phonebook values (NEXT VALUE FOR SEQ, 'hala', 777111001); ");
		stmt.executeUpdate("insert into phonebook values (NEXT VALUE FOR SEQ, 'z','9') ", Statement.RETURN_GENERATED_KEYS);
		
//		ResultSet rs = stmt.executeQuery("select * from phonebook ");
//		if(rs.next()) {System.out.println(rs.getString(2)); }
		System.out.println("db started");
		
		stmt.close();
		conn.close();
	
		
	}
	public void shutdownDB() throws SQLException {
		Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
	}
}
