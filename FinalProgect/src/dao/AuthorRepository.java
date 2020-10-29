package dao;

import java.sql.SQLException;
import java.util.List;

import model.Author;

public interface AuthorRepository {

	Author findById(Integer id) throws SQLException;

	List<Author> findAll() throws SQLException;

	Author create(Author author) throws SQLException;
}