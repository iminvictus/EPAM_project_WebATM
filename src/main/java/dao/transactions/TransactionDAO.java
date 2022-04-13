package dao.transactions;

import dao.DataAccessObject;
import lombok.extern.log4j.Log4j;
import models.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class TransactionDAO extends DataAccessObject<Transaction> {
    private static final String FIND_BY_ID = "SELECT id, userid, type, amount, time FROM transactions WHERE id = ?";
    private static final String FIND_BY_USER_ID = "SELECT id, userid, type, amount, time FROM transactions WHERE userid = ?";
    private static final String FIND_ALL = "SELECT id, userid, type, amount, time FROM transactions";
    private static final String INSERT = "INSERT INTO transactions (userid, type, amount, time) VALUES (?, ?, ?, ?)";

    public TransactionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Transaction findById(long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Transaction transaction = new Transaction();
            while(resultSet.next()) {
                transaction.setId(resultSet.getLong("id"));
                transaction.setUserid(resultSet.getLong("userid"));
                transaction.setType(resultSet.getString("type"));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setTime(ZonedDateTime.of(resultSet.getTimestamp("time").toLocalDateTime(), ZoneId.systemDefault()));
            }
            logger.info("findById method was invoked in TransactionDAO");
            return transaction.getId() != 0 ? transaction : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

    public List<Transaction> findByUserId(long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_USER_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while(resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getLong("id"));
                transaction.setUserid(resultSet.getLong("userid"));
                transaction.setType(resultSet.getString("type"));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setTime(ZonedDateTime.of(resultSet.getTimestamp("time").toLocalDateTime(), ZoneId.systemDefault()));
                transactionsList.add(transaction);
            }
            logger.info("findByUserId method was invoked in TransactionDAO");
            return transactionsList.size() != 0 ? transactionsList : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Transaction> findAll() {
        try(Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            List<Transaction> transactionsList = new ArrayList<>();
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getLong("id"));
                transaction.setUserid(resultSet.getLong("userid"));
                transaction.setType(resultSet.getString("type"));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setTime(ZonedDateTime.of(resultSet.getTimestamp("time").toLocalDateTime(), ZoneId.systemDefault()));
                transactionsList.add(transaction);
            }
            logger.info("findAll method was invoked in TransactionDAO");
            return transactionsList.size() != 0 ? transactionsList : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

    public void save(Transaction transaction) {
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
            statement.setLong(1, transaction.getUserid());
            statement.setString(2, transaction.getType());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setTimestamp(4, Timestamp.valueOf(transaction.getTime().toLocalDateTime()));
            statement.execute();
            logger.info("save method was invoked in TransactionDAO");
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }
}
