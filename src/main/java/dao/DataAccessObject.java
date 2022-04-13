package dao;

import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Log4j
public abstract class DataAccessObject <T extends DataTransferObject> {
    protected final Connection connection;

    public DataAccessObject(Connection connection) {
        super();
        this.connection = connection;
    }

    public abstract T findById(long id);

    public abstract List<T> findAll();

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            logger.error("connection was not closed", ex);
            throw new RuntimeException(ex);
        }
    }
}
