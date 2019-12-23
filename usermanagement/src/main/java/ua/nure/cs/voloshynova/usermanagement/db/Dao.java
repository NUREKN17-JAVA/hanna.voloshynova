package ua.nure.cs.voloshynova.usermanagement.db;

import java.util.Collection;

//import ua.nure.cs.petrykin.usermanagement.domain.User;

public interface Dao <T> {
	T create(T entity) throws DatabaseException;
	
	void update(T entity) throws DatabaseException;
	
	void delete(T entity) throws DatabaseException;
	
	T find(Long id) throws DatabaseException;
	
	Collection <T> findAll() throws DatabaseException;
	
	Collection <T> find(String firstName, String lastName) throws DatabaseException;
	
	public void setConnectionFactory(ConnectionFactory connectionFactory);
}
