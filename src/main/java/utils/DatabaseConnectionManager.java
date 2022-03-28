package utils;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Evgeny Smerdov
 */
public class DatabaseConnectionManager {
    private static final Logger logger = Logger.getLogger(DatabaseConnectionManager.class);
    private static Connection connection;
    private static String host;
    private static String databaseName;
    private static String username;
    private static String password;

    public static Connection getConnection() throws RuntimeException {
        if (connection == null) {
            setCredentials();
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + databaseName, username, password);
            } catch (ClassNotFoundException | SQLException ex) {
                logger.error("postgresql driver was not connected or problems with database credentials", ex);
                throw new RuntimeException(ex);
            }
        }
        return connection;
    }

    private static void setCredentials() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
            host = resourceBundle.getString("postgres.localhost");
            databaseName = resourceBundle.getString("postgres.database");
            username = resourceBundle.getString("postgres.username");
            password = resourceBundle.getString("postgres.password");
        } catch (MissingResourceException ex) {
            logger.error("database resource configuration error", ex);
            throw new RuntimeException(ex);
        }
    }
}
