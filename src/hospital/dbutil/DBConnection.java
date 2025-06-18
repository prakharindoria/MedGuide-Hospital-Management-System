package hospital.dbutil;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static HikariDataSource ds;
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:oracle:thin:@//LAPTOP-Q82125JL:1521/xe");
            config.setUsername("med");
            config.setPassword("admin");
            config.setDriverClassName("oracle.jdbc.OracleDriver"); // Optional: HikariCP can often infer this from jdbcUrl
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            // Recommended settings for performance

            ds = new HikariDataSource(config);
            LOGGER.info("HikariCP Connection Pool initialized successfully.");

            // Add a shutdown hook to close the DataSource
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (ds != null) {
                    LOGGER.info("Closing HikariCP Connection Pool...");
                    ds.close();
                    LOGGER.info("HikariCP Connection Pool closed.");
                }
            }));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing HikariCP Connection Pool", e);
            // System.err.println("Error initializing HikariCP Connection Pool: " + e.getMessage());
            // e.printStackTrace(); // More detailed stack trace to stderr
            throw new RuntimeException("Failed to initialize database connection pool.", e);
        }
    }

    // Private constructor to prevent instantiation
    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (ds == null) {
            LOGGER.severe("HikariDataSource is not initialized. Cannot get connection.");
            throw new SQLException("Database connection pool not initialized.");
        }
        try {
            return ds.getConnection();
        } catch (SQLException sqlex) {
            LOGGER.log(Level.SEVERE, "Error getting connection from HikariCP pool", sqlex);
            // System.err.println("Problem in DataBase connection: " + sqlex.getMessage());
            // sqlex.printStackTrace();
            throw sqlex; // Re-throw to allow caller to handle
        }
    }

    // This method is now for closing the entire pool, typically done at application shutdown.
    // The shutdown hook handles this automatically.
    // This method can be called explicitly if needed before shutdown.
    public static void closeDataSource() {
        if (ds != null && !ds.isClosed()) {
            LOGGER.info("Explicitly closing HikariCP Connection Pool...");
            ds.close();
            LOGGER.info("HikariCP Connection Pool closed explicitly.");
        } else {
            LOGGER.info("HikariCP Connection Pool was already closed or not initialized.");
        }
    }
}
