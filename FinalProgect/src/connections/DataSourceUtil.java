package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DataSourceUtil {
	INSTANCE;

	private Connection[] connections = null;

	public Connection getConnection() throws SQLException {
		if (connections == null) {
			initializeDbConnection();
		}
		Connection conn = null;
		for (int i = 0; i < connections.length; i++) {
			if (connections[i] != null) {
				conn = connections[i];
				connections[i] = null;
				break;
			}
		}
		return conn;
	}

	private void initializeDbConnection() throws SQLException {
		connections = new Connection[10];
		String url = "jdbc:mysql://localhost:3306/library?useUnicode=true&&serverTimezone=UTC&characterEncoding=UTF-8";
		String username = "root";
		String password = "Polinka2012";

		while (hasFreeSlots()) {
			Connection conn = new MyCustomConnection(DriverManager.getConnection(url, username, password));
			returnConnection(conn);
		}
	}

	private boolean hasFreeSlots() {
		boolean isFreeSlotAvailable = false;
		for (int i = 0; i < connections.length; i++) {
			if (connections[i] == null) {
				isFreeSlotAvailable = true;
				break;
			}
		}
		return isFreeSlotAvailable;
	}

	public void returnConnection(Connection conn) {
		if (conn instanceof MyCustomConnection) {
			for (int i = 0; i < connections.length; i++) {
				if (connections[i] == null) {
					connections[i] = conn;
					return;
				}
			}
		} else {
			throw new IllegalArgumentException("Это соединение не из этого ConnectionPool-а!");
		}
	}
}
