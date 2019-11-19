package ua.nure.cs.voloshynova.usermanagement.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;

import ua.nure.cs.voloshynova.usermanagement.domain.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {

	private static final String LAST_NAME = "Due";
	private static final String FIRST_NAME = "John";
	
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;
	
	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}
	
	public void testCreate() throws DatabaseException {
		User user = new User();
		user.setFirstName(FIRST_NAME);
		user.setLastName(LAST_NAME);
		user.setDateOfBirth(new Date());
		assertNull(user.getId());
		User userToChek = dao.create(user);
		assertNotNull(userToChek);
		assertNotNull(userToChek.getId());
		assertEquals(user.getFirstName(),userToChek.getFirstName());
		assertEquals(user.getLastName(),userToChek.getLastName());
		assertEquals(user.getDateOfBirth(),userToChek.getDateOfBirth());		
	}
	public void testUpdate() throws DatabaseException{
		User user = new User();
		user.setFirstName("Misha");
		user.setLastName("Petrykin");
		user.setDateOfBirth(new Date());
		user.setId(1000L);
		dao.update(user);
		assertNotNull(user);
		assertNotNull(user.getId());
		assertNotNull(user.getFirstName());
		assertNotNull(user.getLastName());
		assertNotNull(user.getDateOfBirth());
	}
	public void testDelete() throws DatabaseException{
		User user = new User();
		user.setId(1000L);
		assertNotNull(dao.find(1000L));
	}
	
	public void testFind() throws DatabaseException{
		User userToChek = dao.find(1000L);
		assertEquals("Error","Gates",userToChek.getLastName());
		assertEquals("Error","Bill",userToChek.getFirstName());
	
	}
	
	public void testFindAll() throws DatabaseException {
		Collection <User> users= dao.findAll();
		assertNotNull(users);
		assertEquals("Collection size does not match",2, users.size());
	}
	//getDataSet().getTable("users").
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver"
				,"jdbc:hsqldb:file:db/usermanagement"
				,"sa"
				,"");
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet=new XmlDataSet(
				getClass().
				getClassLoader().
				getResourceAsStream("usersDataSet.xml")
				);
		return dataSet;
	}
	
	
	
}
