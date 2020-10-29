package service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.AuthorRepository;
import model.Author;

public class AuthorServiceImpl implements AuthorService {

	private final Logger LOG = Logger.getLogger(AuthorServiceImpl.class.getName());
	private final AuthorRepository authorRepository;

	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public Author findById(Integer id) throws SQLException {
		LOG.log(Level.INFO, "getById(" + id + ") method called");
		return authorRepository.findById(id);
	}

	@Override
	public List<Author> findAll() throws SQLException {
		LOG.log(Level.INFO, "getAll method called");
		return authorRepository.findAll();

	}

	@Override
	public Author create(Author author) throws SQLException {
		return authorRepository.create(author);
	}

}
