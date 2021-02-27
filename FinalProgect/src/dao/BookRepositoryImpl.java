package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connections.DataSourceUtil;
import model.Author;
import model.Book;
import model.Genre;

public class BookRepositoryImpl implements BookRepository {

	public Book findById(Integer id) throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		PreparedStatement ps = conn.prepareStatement("select  * from book where id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		List<Book> books = createBook(rs);
		DataSourceUtil.INSTANCE.returnConnection(conn);
		return books.isEmpty() ? null : books.get(0);
	}

	public List<Book> findAll() throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("select  * from book");
		List<Book> books = createBook(rs);
		DataSourceUtil.INSTANCE.returnConnection(conn);
		return books;
	}

	public Book create(Book book) throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		PreparedStatement ps = conn.prepareStatement("insert into book (name, authorID, genreID) values (?,?,?)");
		ps.setString(1, book.getName());
		AuthorRepositoryImpl ari = new AuthorRepositoryImpl();
		List<Author> authors = ari.findAll();
		if (!authors.contains(book.getA())) {
			ari.create(book.getA());
		}
		ps.setInt(2, book.getA().getId());
		GenreRepositoryImpl gri = new GenreRepositoryImpl();
		List<Genre> genres = gri.findAll();
		if (!genres.contains(book.getG())) {
			gri.create(book.getG());
		}
		ps.setInt(3, book.getG().getId());
		ps.executeUpdate();
		List<Book> books = findAll();
		for (Book b : books) {
			if (book.getName().equals(b.getName())) {
				return b;
			}
		}
		DataSourceUtil.INSTANCE.returnConnection(conn);
		return null;
	}

	public Book update(Book book) throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		PreparedStatement ps = conn.prepareStatement("UPDATE book SET name = ?, authorID = ?, genreID=? WHERE id=?");
		ps.setString(1, book.getName());
		AuthorRepositoryImpl ari = new AuthorRepositoryImpl();
		List<Author> authors = ari.findAll();
		if (!authors.contains(book.getA())) {
			ari.create(book.getA());
		}
		ps.setInt(2, book.getA().getId());
		GenreRepositoryImpl gri = new GenreRepositoryImpl();
		List<Genre> genres = gri.findAll();
		if (!genres.contains(book.getG())) {
			gri.create(book.getG());
		}
		ps.setInt(3, book.getG().getId());
		ps.setInt(4, book.getId());
		ps.executeUpdate();
		Book books = findById(book.getId());
		DataSourceUtil.INSTANCE.returnConnection(conn);
		return books;
	}

	public void delete(Integer id) throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		PreparedStatement ps = conn.prepareStatement("delete from book where id = ?");
		ps.setInt(1, id);
		ps.executeUpdate();
		DataSourceUtil.INSTANCE.returnConnection(conn);
	}

	private List<Book> createBook(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		while (rs.next()) {
			Book b = new Book();
			b.setId(rs.getInt("id"));
			b.setName(rs.getString("name"));
			AuthorRepositoryImpl ari = new AuthorRepositoryImpl();
			b.setA(ari.findById(rs.getInt("authorID")));
			GenreRepositoryImpl gri = new GenreRepositoryImpl();
			b.setG(gri.findById(rs.getInt("genreID")));
			books.add(b);
		}
		return books;
	}

}
