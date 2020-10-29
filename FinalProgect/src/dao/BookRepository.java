package dao;

import java.sql.SQLException;
import java.util.List;

import model.Book;

public interface BookRepository {

	
	Book findById(Integer id) throws SQLException;

	List<Book> findAll() throws SQLException;

	Book create(Book book) throws SQLException;

	Book update(Book book) throws SQLException;

	void delete(Integer id) throws SQLException;
}
