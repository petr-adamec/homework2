package homework.homework.daoImpl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import homework.homework.dao.PhoneBookDao;
import homework.homework.entity.PhoneBookEntity;

public class PhoneBookDaoImpl extends AbstractDao implements PhoneBookDao {

	protected static final Logger log = Logger.getLogger("PhoneBookDaoImpl");

	public PhoneBookDaoImpl() {
		try {
			setConn(DriverManager.getConnection("jdbc:h2:~/test", "sa", ""));
		} catch (SQLException e) {
			e.printStackTrace();
			log.log(Level.SEVERE, "Cannot create connection.", e);
		}
	}

	public void addEntry(PhoneBookEntity entry) throws Exception {
		Statement stmt = getStatement();
		int key = stmt.executeUpdate("insert into phonebook values ( NEXT VALUE FOR SEQ, '" + entry.getName() + "','"
				+ entry.getPhone() + "')", Statement.RETURN_GENERATED_KEYS);
		entry.setId(key);
		stmt.close();
	}

	// @Delete("DELETE FROM phonebook WHERE id = #{id}")
	public void removeEntry(Integer id) throws Exception {
		Statement stmt = getStatement();
		try {
			stmt.executeUpdate("delete from phonebook where id = " + id);
		} finally {
			stmt.close();
		}

	}

	// @Select("SELECT * FROM phonebook WHERE name like'%#{name}%' ")
	public List<PhoneBookEntity> findByName(String name) throws Exception {
		Statement stmt = getStatement();
		ResultSet rs;
		List<PhoneBookEntity> ret;
		try {
			rs = stmt.executeQuery("select * from phonebook where name like '%" + name + "%'");
			ret = toList(rs);
		} finally {
			stmt.close();
		}
		return ret;
	}

	// @Select("SELECT * FROM phonebook")
	public List<PhoneBookEntity> list() throws Exception {
		Statement stmt = getStatement();
		ResultSet rs;
		List<PhoneBookEntity> ret = null;
		try {
			rs = stmt.executeQuery("select * from phonebook ");
			ret = toList(rs);
		} finally {
			stmt.close();
		}
		return ret;

	}

	public PhoneBookEntity findByNameAndPhone(String name, String phone) throws Exception {
		Statement stmt = getStatement();
		ResultSet rs;
		List<PhoneBookEntity> ret;
		try {
			rs = stmt.executeQuery("select * from phonebook where name = '" + name + "' and phone = '" + phone + "' ");
			ret = toList(rs);
		} finally {
			stmt.close();
		}
		return ret == null ? null : ret.get(0);
	}

	private List<PhoneBookEntity> toList(ResultSet rs) throws Exception {
		List<PhoneBookEntity> list = new ArrayList<PhoneBookEntity>();

		while (rs.next()) {
			int id = rs.getInt(1);
			String name = rs.getString(2);
			String phone = rs.getString(3);
			PhoneBookEntity entity = new PhoneBookEntity(id, name, phone);
			list.add(entity);
		}
		return list;
	}

	public PhoneBookEntity findByPhone(String phone) throws Exception {
		Statement stmt = getStatement();
		ResultSet rs;
		List<PhoneBookEntity> ret;
		try {
			rs = stmt.executeQuery("select * from phonebook where  phone = '" + phone + "' ");
			ret = toList(rs);
		} finally {
			stmt.close();
		}
		return (ret == null || ret.size() == 0) ? null : ret.get(0);
	}

}
