package dao;

import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Evgeny Smerdov
 * @param <T> any entity class of database that inherits DataTransferObject
 */
@Log4j
public abstract class DataAccessObject <T extends DataTransferObject> {
    protected final Connection connection;

    /**
     * Establish connection to the DataAccessObject
     * @param connection the connection to the database
     */
    public DataAccessObject(Connection connection) {
        super();
        this.connection = connection;
    }

    /**
     * This method closing connection with database
     */
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            logger.error("connection was not closed", ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * This method must find an entity with a specific id in the database
     * @param id the primary key of the entity being searched for
     * @return entity object with a specific id from the database
     */
    public abstract T findById(long id);

    /**
     * This method must find all certain entities in the database
     * @return list of certain entity objects from the database
     */
    public abstract List<T> findAll();
}
