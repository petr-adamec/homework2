package homework.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;

import homework.homework.dto.PhoneBookDto;
import homework.homework.service.PhoneBookService;
import homework.homework.serviceImpl.PhoneBookServiceImpl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	Comparator dtoCompare = new Comparator() {

		public int compare(Object o1, Object o2) {
			PhoneBookDto p1 = (PhoneBookDto) o1;
			PhoneBookDto p2 = (PhoneBookDto) o2;
			return (p1.getName() + p1.getPhone()).compareTo(p2.getName() + p2.getPhone());

		}

	};

	PhoneBookService service;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		service = new PhoneBookServiceImpl();
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
		Statement stmt = conn.createStatement();

		stmt.executeLargeUpdate("delete from phonebook");
		stmt.close();
		conn.close();
	}

	/**
	 * TestCase1 - adding new entry
	 */

	public void testUnitAddEntry() {

		PhoneBookDto testDto = new PhoneBookDto("TestName1", "723845632");
		service.addEntry(testDto);
		List<PhoneBookDto> list = service.list();

		assertEquals("Should contain exactly one entry.", 1, list.size());
		PhoneBookDto dto = list.get(0);
		assertEquals("the name", testDto.getName(), dto.getName());
		assertEquals("the phone", testDto.getPhone(), dto.getPhone());

	}

}
