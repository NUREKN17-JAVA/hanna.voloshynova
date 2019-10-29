package ua.nure.cs.voloshynova.usermanagement;

import java.util.Calendar;
import junit.framework.TestCase;

public class userTest extends TestCase {

	private static final int ETALONE_OF_AGE1 = 19;
	private static final int ETALONE_OF_AGE2= 21;
	private static final int MONTH_OF_BIRTH = 4;
	private static final int YEAR_OF_BIRTH = 2000;
	private static final int DAY_OF_BIRTH = 11;
	private static final int MONTH_OF_BIRTH2 =Calendar.MONTH ;
	private static final int DAY_OF_BIRTH2 = Calendar.DATE;
	private static final int YEAR_OF_BIRTH5 = 2030;
	private static final int MONTH_OF_BIRTH_6=10;
	private static final int DAY_OF_BIRTH_6=15;
	private static final int YEAR_OF_BIRTH_6=2000;
	private static final int MONTH_OF_BIRTH_7=12;
	private static final int DAY_OF_BIRTH_7=31;
	private static final int YEAR_OF_BIRTH_7=1999;
	private user user;
	private Calendar calendar;
	
	public void testGetFullName() {
		user.setFirstName("John");
		user.setLastName("Doe");
		assertEquals("Doe, John", user.getFullName());
	}
	
	public void testGetAge1() {
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH, DAY_OF_BIRTH);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_OF_AGE1, user.getAge());
	}

	public void testGetAge2() {
		calendar.set(YEAR_OF_BIRTH,MONTH_OF_BIRTH,DAY_OF_BIRTH);
		user.setDateOfBirth(calendar.getTime());
		assertTrue("Your age is less than 21!",user.getAge()<ETALONE_OF_AGE2);
	}
	public void testGetAge3() {
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH2, DAY_OF_BIRTH2);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_OF_AGE1,user.getAge());
	}
	public void testGetAge4() {
		calendar.set(YEAR_OF_BIRTH, MONTH_OF_BIRTH2, DAY_OF_BIRTH2);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_OF_AGE1,user.getAge());
	}
	public void testGetAge5() {
		calendar.set(YEAR_OF_BIRTH5, MONTH_OF_BIRTH, DAY_OF_BIRTH);
		user.setDateOfBirth(calendar.getTime());
		assertTrue("Wrong user`s year of birth",user.getAge()<0);
	}
	public void testGetAge6() {
		calendar.set(YEAR_OF_BIRTH_6,MONTH_OF_BIRTH_6,DAY_OF_BIRTH_6);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_OF_AGE1,user.getAge());
	}
	public void testGetAge7() {
		calendar.set(YEAR_OF_BIRTH_7,MONTH_OF_BIRTH_7,DAY_OF_BIRTH_7);
		user.setDateOfBirth(calendar.getTime());
		assertEquals(ETALONE_OF_AGE1,user.getAge());
	}
	protected void setUp() throws Exception {
		super.setUp();
		user = new user();
		calendar=Calendar.getInstance();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
