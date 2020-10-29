package service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.GenreRepository;
import model.Genre;

public class GenreServiceImpl implements GenreService {

	private final Logger LOG = Logger.getLogger(GenreServiceImpl.class.getName());
	private final GenreRepository genreRepository;

	public GenreServiceImpl(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	@Override
	public Genre findById(Integer id) throws SQLException {
		LOG.log(Level.INFO, "getById(" + id + ") method called");
		return genreRepository.findById(id);
	}

	@Override
	public List<Genre> findAll() throws SQLException {
		LOG.log(Level.INFO, "getAll method called");
		return genreRepository.findAll();
	}

	@Override
	public Genre create(Genre genre) throws SQLException {
		return genreRepository.create(genre);
	}

}
