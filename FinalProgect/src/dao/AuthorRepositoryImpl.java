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

public class AuthorRepositoryImpl implements AuthorRepository {

	@Override
	public Author findById(Integer id) throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		PreparedStatement ps = conn.prepareStatement("select  * from author where id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		List<Author> authors = createAuthor(rs);
		DataSourceUtil.INSTANCE.returnConnection(conn);
		return authors.isEmpty() ? null : authors.get(0);
	}

	private List<Author> createAuthor(ResultSet rs) throws SQLException {
		List<Author> author = new ArrayList<Author>();
		while (rs.next()) {
			Author g = new Author();
			g.setId(rs.getInt("id"));
			g.setName(rs.getString("name"));

			author.add(g);
		}
		return author;
	}

	@Override
	public List<Author> findAll() throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("select  * from author");
		List<Author> authors = createAuthor(rs);
		DataSourceUtil.INSTANCE.returnConnection(conn);
		return authors;
	}

	@Override
	public Author create(Author author) throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		PreparedStatement ps = conn.prepareStatement("insert into author (name) values (?)");
		ps.setString(1, author.getName());
		ps.executeUpdate();
		List<Author> authors = findAll();
		for (Author a : authors) {
			if (author.getName().equals(a.getName())) {
				return a;
			}
			DataSourceUtil.INSTANCE.returnConnection(conn);
		}
		return null;
	}

}
