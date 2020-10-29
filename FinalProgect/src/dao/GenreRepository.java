package dao;

import java.sql.SQLException;
import java.util.List;

import model.Genre;

public interface GenreRepository {

	Genre findById(Integer id) throws SQLException;

	List<Genre> findAll() throws SQLException;

	Genre create(Genre genre) throws SQLException;
}
