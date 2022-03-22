package utils;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Evgeny Smerdov
 */
public class DatabaseConnectionManager {
    private static final Logger logger = Logger.getLogger(DatabaseConnectionManager.class);

    private final String host;
    private final String username;
    private final String password;
    private final String databaseName;

    /**
     * This constructor assigns database user credentials
     * @param host host of the database (localhost)
     * @param username database owner username
     * @param password database owner password
     * @param databaseName the name of the database used
     */
    public DatabaseConnectionManager(String host, String username, String password, String databaseName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
    }

    /**
     * This method provides connection to the database
     * @return connection to the database
     * @throws SQLException if the connection is not provgided.
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            logger.error("postgresql driver was not connected", ex);
            throw new RuntimeException(ex);
        }
        return DriverManager.getConnection("jdbc:postgresql://" + host + "/" + databaseName, username, password);
    }
}
