package ua.nure.cs.voloshynova.usermanagement.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.cs.voloshynova.usermanagement.db.DaoFactory;
import ua.nure.cs.voloshynova.usermanagement.db.DaoFactoryImpl;
import ua.nure.cs.voloshynova.usermanagement.db.MockDaoFactory;
import ua.nure.cs.voloshynova.usermanagement.db.MockUserDao;
import ua.nure.cs.voloshynova.usermanagement.domain.User;
import ua.nure.cs.voloshynova.usermanagement.gui.MainFrame;
import ua.nure.cs.voloshynova.usermanagement.util.Messages;

public class MainFrameTest extends JFCTestCase {
	private static final String MOCK_FIND_ALL_COMMAND = "findAll";
	private static final String MOCK_DELETE_COMMAND = "delete";
	private static final String MOCK_UPDATE_COMMAND = "update";
	private static final String MOCK_CREATE_COMMAND = "create";
	
	private static final String USER_TABLE_MODEL_LAST_NAME = "UserTableModel.last_name";
	private static final String USER_TABLE_MODEL_FIRST_NAME = "UserTableModel.first_name";
	private static final String USER_TABLE_MODEL_ID = "UserTableModel.id";
	
	private static final String DATE_OF_BIRTH_FIELD_COMPONENT_NAME = "dateOfBirthField";
	private static final String LAST_NAME_FIELD_COMPONENT_NAME = "lastNameField";
	private static final String FIRST_NAME_FIELD_COMPONENT_NAME = "firstNameField";
	
	private static final String LAST_NAME = "Doe";
	private static final String FIRST_NAME = "John";
	private static final Date DATE_OF_BIRTH = new Date();
	
	private static final String EXPECTED_USER_LAST_NAME = "Gates";
	private static final String EXPECTED_USER_FIRST_NAME = "Bill";
	private static final Long EXPECTED_USER_ID = new Long(1000);
	
	private static final String BROWSE_PANEL_COMPONENT_NAME = "browsePanel";
	private static final String USER_TABLE_COMPONENT_NAME = "userTable";
	private static final String ADD_PANEL_COMPONENT_NAME = "addPanel";
	private static final String EDIT_PANEL_COMPONENT_NAME = "editPanel";
	private static final String DETAILS_PANEL_COMPONENT_NAME = "detailsPanel";
	
	private static final String OK_BUTTON_COMPONENT_NAME = "okButton";
	private static final String DELETE_BUTTON_COMPONENT_NAME = "deleteButton";
	private static final String DETAILS_BUTTON_COMPONENT_NAME = "detailsButton";
	private static final String CANCEL_BUTTON_COMPONENT_NAME = "cancelButton";
	private static final String EDIT_BUTTON_COMPONENT_NAME = "editButton";
	private static final String ADD_BUTTON_COMPONENT_NAME = "addButton";
	private static final int NUMBER_OF_COLUMNS_IN_USER_TABLE = 3;
	private static final int NUMBER_OF_ROWS_ADD_TEST = 2;
	private static final int NUMBER_OF_ROWS_ADD_CANCEL_TEST = 1;
	
	private MainFrame mainFrame;
	private Mock mockUserDao;
	private ArrayList<User> users;
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		try {
		Properties properties = new Properties();

		properties.setProperty("dao.Factory", MockDaoFactory.class.getName());
		DaoFactory.init(properties);
		mockUserDao =((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
		
		User expectedUser = new User(EXPECTED_USER_ID,EXPECTED_USER_FIRST_NAME,EXPECTED_USER_LAST_NAME,DATE_OF_BIRTH);
		
		users = new ArrayList<User>();
		users.add(expectedUser);
		
		mockUserDao.expectAndReturn(MOCK_FIND_ALL_COMMAND, users);
		setHelper(new JFCTestHelper());
		mainFrame = new MainFrame();
		}catch(Exception e) {
			e.printStackTrace();
		}
		mainFrame.setVisible(true);
	}

	@Override
	protected void tearDown() throws Exception {
		try {
				super.tearDown();
				mockUserDao.verify();
//				mainFrame.setVisible(false);
				getHelper();
				TestHelper.cleanUp(this);
		} catch (Exception e){
			e.printStackTrace();
			}
	}//done
	
	private Component find(Class<?> componentClass, String name) {
		NamedComponentFinder finder;
		finder = new NamedComponentFinder(componentClass, name);
		finder.setWait(0);
		Component component = finder.find(mainFrame, 0);
		assertNotNull("Could not find component '" + name + "'", component);
		return component;
	}//done
	
	public void testBrowseTablePanel() {
		JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
		find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
		assertEquals(NUMBER_OF_COLUMNS_IN_USER_TABLE, table.getColumnCount());
		assertEquals(Messages.getString(USER_TABLE_MODEL_ID), table.getColumnName(0));
		assertEquals(Messages.getString(USER_TABLE_MODEL_FIRST_NAME), table.getColumnName(1));
		assertEquals(Messages.getString(USER_TABLE_MODEL_LAST_NAME), table.getColumnName(2));
		find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
		find(JButton.class, EDIT_BUTTON_COMPONENT_NAME);
		find(JButton.class, DETAILS_BUTTON_COMPONENT_NAME);
		find(JButton.class, DELETE_BUTTON_COMPONENT_NAME);
		find(JTable.class,USER_TABLE_COMPONENT_NAME);
	}
	
	public void testAddUser() {
		
		User user = new User(FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
		User expectedUser = new User(new Long(1),FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
		mockUserDao.expectAndReturn(MOCK_CREATE_COMMAND, user, expectedUser);
		ArrayList<User> users = new ArrayList<User>(this.users);
		users.add(expectedUser);
		mockUserDao.expectAndReturn(MOCK_FIND_ALL_COMMAND,users);
		JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
		assertEquals(0,table.getRowCount());
		
		JButton addButton = (JButton) find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
		getHelper().enterClickAndLeave(new MouseEventData(this,addButton));
		find(JPanel.class, ADD_PANEL_COMPONENT_NAME);
		fillFields(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
		
		JButton okButton = (JButton) find(JButton.class,OK_BUTTON_COMPONENT_NAME);
		
		getHelper().enterClickAndLeave(new MouseEventData(this,okButton));
		find(JPanel.class,BROWSE_PANEL_COMPONENT_NAME);
		table = (JTable) find(JTable.class,USER_TABLE_COMPONENT_NAME);
		assertEquals(NUMBER_OF_ROWS_ADD_TEST,table.getRowCount());
	}
	public void testCancelAddUser() {
		
		User user = new User(FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
		User expectedUser = new User(new Long(1),FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
		mockUserDao.expectAndReturn(MOCK_CREATE_COMMAND, user, expectedUser);
		ArrayList<User> users = new ArrayList<User>(this.users);
		users.add(expectedUser);
		mockUserDao.expectAndReturn(MOCK_FIND_ALL_COMMAND,users);
		JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
		assertEquals(0,table.getRowCount());
		
		JButton addButton = (JButton) find(JButton.class, ADD_BUTTON_COMPONENT_NAME);
		getHelper().enterClickAndLeave(new MouseEventData(this,addButton));
		find(JPanel.class, ADD_PANEL_COMPONENT_NAME);
		fillFields(FIRST_NAME, LAST_NAME, DATE_OF_BIRTH);
		
		JButton cancelButton = (JButton) find(JButton.class,CANCEL_BUTTON_COMPONENT_NAME);
		
		getHelper().enterClickAndLeave(new MouseEventData(this,cancelButton));
		find(JPanel.class,BROWSE_PANEL_COMPONENT_NAME);
		table = (JTable) find(JTable.class,USER_TABLE_COMPONENT_NAME);
		assertEquals(NUMBER_OF_ROWS_ADD_CANCEL_TEST,table.getRowCount());
		
	}
	public void testEditUser() {
		find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
	    User expectedUser = new User(EXPECTED_USER_ID,EXPECTED_USER_FIRST_NAME,EXPECTED_USER_LAST_NAME,new Date());
	    mockUserDao.expect(MOCK_UPDATE_COMMAND, expectedUser);
	    ArrayList<User> users = new ArrayList<User>(this.users);
	    users.add(expectedUser);
	    
	    mockUserDao.expectAndReturn(MOCK_FIND_ALL_COMMAND, users);
	    JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
	    assertEquals(1, table.getRowCount());
	    JButton editButton = (JButton) find(JButton.class, EDIT_BUTTON_COMPONENT_NAME);
	    getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	    getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
	    
	    find(JPanel.class, EDIT_PANEL_COMPONENT_NAME);
	    JTextField firstNameField = (JTextField) find(JTextField.class,FIRST_NAME_FIELD_COMPONENT_NAME);
	    JTextField lastNameField = (JTextField) find(JTextField.class,LAST_NAME_FIELD_COMPONENT_NAME);
	    getHelper().sendString(new StringEventData(this, firstNameField, "1"));
	    getHelper().sendString(new StringEventData(this, lastNameField, "1"));
	    
	    JButton okButton = (JButton) find(JButton.class, OK_BUTTON_COMPONENT_NAME);
	    getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
	    find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
	    table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
	    assertEquals(2, table.getRowCount());
	}
	public void testCancelEditUser() {
		User expectedUser = new User(new Long(1),FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
		ArrayList<User> users = new ArrayList<User>(this.users);
		users.add(expectedUser);
		
		mockUserDao.expectAndReturn("findAll",users);
		
		JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
		assertEquals(1,table.getRowCount());
		
		JButton editButton = (JButton) find(JTable.class,EDIT_BUTTON_COMPONENT_NAME);
		getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
		getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
		
		find(JPanel.class,EDIT_PANEL_COMPONENT_NAME);
		
		JTextField firstNameField = (JTextField) find(JTextField.class,FIRST_NAME_FIELD_COMPONENT_NAME);
		JTextField lastNameField = (JTextField) find(JTextField.class,LAST_NAME_FIELD_COMPONENT_NAME);
		JTextField dateOfBirthField = (JTextField) find(JTextField.class,DATE_OF_BIRTH_FIELD_COMPONENT_NAME);
		
		assertEquals(EXPECTED_USER_FIRST_NAME,firstNameField.getText());
		assertEquals(EXPECTED_USER_LAST_NAME,lastNameField.getText());
		
		getHelper().sendString(new StringEventData(this,firstNameField,FIRST_NAME));
		getHelper().sendString(new StringEventData(this,lastNameField,LAST_NAME));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(DATE_OF_BIRTH);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
		
		JButton cancelButton = (JButton) find(JButton.class, CANCEL_BUTTON_COMPONENT_NAME);
		getHelper().enterClickAndLeave(new MouseEventData(this,cancelButton));
		
		find(JPanel.class,BROWSE_PANEL_COMPONENT_NAME);
		table = (JTable) find(JTable.class,USER_TABLE_COMPONENT_NAME);
		assertEquals(2,table.getRowCount());
		mockUserDao.verify();
		
	}
	public void testDetailsUser(){
	    User expectedUser = new User(new Long(1),FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
	    ArrayList<User> users = new ArrayList<User>(this.users);
	    users.add(expectedUser);
	    
	    mockUserDao.expectAndReturn("findAll", users);
	    JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
	    assertEquals(1, table.getRowCount());
	    
	    JButton detailsButton = (JButton)find(JButton.class, DETAILS_BUTTON_COMPONENT_NAME);
	    getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	    getHelper().enterClickAndLeave(new MouseEventData(this, detailsButton));
	    
	    find(JPanel.class, DETAILS_PANEL_COMPONENT_NAME);
	    find(JTextField.class, FIRST_NAME_FIELD_COMPONENT_NAME);
	    find(JTextField.class, LAST_NAME_FIELD_COMPONENT_NAME);
	    find(JTextField.class, DATE_OF_BIRTH_FIELD_COMPONENT_NAME);
	    
	    JButton cancelButton = (JButton)find(JButton.class, CANCEL_BUTTON_COMPONENT_NAME);
	    getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));
	    
	    find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
	    table = (JTable)find(JTable.class, USER_TABLE_COMPONENT_NAME);
	    assertEquals(1, table.getRowCount());
	  }
	public void testDeleteUser() {
		User expectedUser = new User(EXPECTED_USER_ID,EXPECTED_USER_FIRST_NAME,EXPECTED_USER_LAST_NAME,new Date());
        mockUserDao.expect(MOCK_DELETE_COMMAND, expectedUser);
        ArrayList<User> users = new ArrayList<User>();
        mockUserDao.expectAndReturn(MOCK_FIND_ALL_COMMAND, users);
        JTable table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        assertEquals(1, table.getRowCount());
        JButton deleteButton = (JButton) find(JButton.class, DELETE_BUTTON_COMPONENT_NAME);
        getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
        dialog("Delete user");
        getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
        find(JPanel.class, BROWSE_PANEL_COMPONENT_NAME);
        table = (JTable) find(JTable.class, USER_TABLE_COMPONENT_NAME);
        assertEquals(0, table.getRowCount());
	}
	private void dialog(String title) {
		DialogFinder dialogFinder = new DialogFinder(title);
		JDialog dialog = (JDialog) dialogFinder.find();
        assertNotNull("Could not find the dialog '" + title + "'", dialog);
        getHelper();
        TestHelper.disposeWindow(dialog, this);
	}
	
	private void fillFields(String firstName, String lastName, Date dateOfBirth) {
		JTextField firtNameField = (JTextField) find(JTextField.class, FIRST_NAME_FIELD_COMPONENT_NAME);
		JTextField lastNameField = (JTextField) find(JTextField.class, LAST_NAME_FIELD_COMPONENT_NAME);
		JTextField dateOfBirthField = (JTextField) find(JTextField.class, DATE_OF_BIRTH_FIELD_COMPONENT_NAME);
		
		getHelper().sendString(new StringEventData(this, firtNameField, firstName));
		getHelper().sendString(new StringEventData(this, lastNameField, lastName));
		DateFormat formatter = DateFormat.getDateInstance();
		String date = formatter.format(dateOfBirth);
		getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
	}
	
}