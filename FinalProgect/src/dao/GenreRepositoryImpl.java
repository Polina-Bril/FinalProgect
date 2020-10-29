package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connections.DataSourceUtil;
import model.Genre;

public class GenreRepositoryImpl implements GenreRepository {

	@Override
	public Genre findById(Integer id) throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		PreparedStatement ps = conn.prepareStatement("select  * from genre where id = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		List<Genre> genres = createGenre(rs);
		DataSourceUtil.INSTANCE.returnConnection(conn);
		return genres.isEmpty() ? null : genres.get(0);
	}

	private List<Genre> createGenre(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<Genre>();
		while (rs.next()) {
			Genre g = new Genre();
			g.setId(rs.getInt("id"));
			g.setName(rs.getString("name"));

			genres.add(g);
		}
		return genres;
	}

	@Override
	public List<Genre> findAll() throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("select  * from genre");
		List<Genre> genres = createGenre(rs);
		DataSourceUtil.INSTANCE.returnConnection(conn);
		return genres;
	}

	@Override
	public Genre create(Genre genre) throws SQLException {
		Connection conn = DataSourceUtil.INSTANCE.getConnection();
		PreparedStatement ps = conn.prepareStatement("insert into genre (name) values (?)");
		ps.setString(1, genre.getName());
		ps.executeUpdate();
		List<Genre> genres = findAll();
		for (Genre g : genres) {
			if (g.getName().equals(genre.getName())) {
				return g;
			}
			DataSourceUtil.INSTANCE.returnConnection(conn);
		}
		return null;
	}
}
