package dao.transactions;

import dao.DataAccessObject;
import models.Transaction;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransactionDAO extends DataAccessObject<Transaction> {

    private static Logger logger = Logger.getLogger(TransactionDAO.class);
    private static final String INSERT = "INSERT INTO transactions (type, amount, time, userid) VALUES (?, ?, ?, ?)";

    /**
     * Establish connection to the DataAccessObject
     *
     * @param connection the connection to the database
     */
    public TransactionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Transaction findById(long id) {
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        return null;
    }

    public void save(Transaction transaction) {
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
            statement.setString(1, transaction.getType());
            statement.setBigDecimal(2, transaction.getAmount());
            statement.setTimestamp(3, transaction.getTime());
            statement.setLong(4, transaction.getUserid());
            statement.execute();
            logger.info("save method was invoked in TransactionDAO");
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }
}
