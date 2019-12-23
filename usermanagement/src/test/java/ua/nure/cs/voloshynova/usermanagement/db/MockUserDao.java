package ua.nure.cs.voloshynova.usermanagement.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ua.nure.cs.voloshynova.usermanagement.db.ConnectionFactory;
import ua.nure.cs.voloshynova.usermanagement.db.Dao;
import ua.nure.cs.voloshynova.usermanagement.db.DatabaseException;
import ua.nure.cs.voloshynova.usermanagement.domain.User;

public class MockUserDao implements Dao<User> {

    private long id = 0;
    private Map<Long, User> users = new HashMap<>();

    public User create(User user) throws DatabaseException {
        Long currentId = new Long(++id);
        user.setId(currentId);
        users.put(currentId, user);
        return user;
    }

    public void update(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    public void delete(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
    }

    public User find(Long id) throws DatabaseException {
        
        return (User) users.get(id);
    }

    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }

	@Override
	public void setConnectionFactory(ConnectionFactory connectionFactory) {

	}
	public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

	
}
