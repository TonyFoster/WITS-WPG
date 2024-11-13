package tw.mcark.tony;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceManager {

    private static final DataSourceManager instance = new DataSourceManager();
    private final HikariDataSource dataSource;

    private DataSourceManager() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/wits");
        config.setUsername(System.getenv("DB_WITS_USERNAME")); // Use environment variables
        config.setPassword(System.getenv("DB_WITS_PASSWORD"));
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30 * 1000);
        config.setConnectionTimeout(20 * 1000);
        config.setMaxLifetime(30 * 60 * 1000);

        dataSource = new HikariDataSource(config);
    }

    public static DataSourceManager getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (!dataSource.isClosed()) {
            dataSource.close();
        }
    }
}