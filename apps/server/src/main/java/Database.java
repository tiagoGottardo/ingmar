import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
  private static Database instance;
  private HikariDataSource dataSource;

  private Database() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:postgresql://localhost:5432/ingmar");
    config.setUsername("postgres");
    config.setPassword("password");
    dataSource = new HikariDataSource(config);
  }

  public static synchronized Database getInstance() {
    if (instance == null) instance = new Database();
    return instance;
  }

  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

}
