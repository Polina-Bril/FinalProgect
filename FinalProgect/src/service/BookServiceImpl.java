package service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.BookRepository;
import model.Book;

public class BookServiceImpl implements BookService {

	private final Logger LOG = Logger.getLogger(BookServiceImpl.class.getName());
	private final BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Book findById(Integer id) throws SQLException {
		LOG.log(Level.INFO, "getById(" + id + ") method called");
		return bookRepository.findById(id);

	}

	public List<Book> findAll() throws SQLException {
		LOG.log(Level.INFO, "getAll method called");
		return bookRepository.findAll();

	}

	public Book create(Book book) throws SQLException {
		return bookRepository.create(book);
	}

	public Book update(Book book) throws SQLException {
		return bookRepository.update(book);
	}

	public void delete(Integer id) throws Exception {
		bookRepository.delete(id);
	}

}
