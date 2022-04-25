package dao.transactions;

import dao.DataAccessObject;
import dao.cards.CardDAO;
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
    private static final String FIND_BY_ID = "SELECT id_transaction, date, amount, type, initiated_by, state, id_card FROM transactions WHERE id_transaction = ?";
    private static final String FIND_BY_CARD_ID = "SELECT id_transaction, date, amount, type, initiated_by, state, id_card FROM transactions WHERE id_card = ?";
    private static final String FIND_ALL = "SELECT id_transaction, date, amount, type, initiated_by, state, id_card FROM transactions";
    private static final String INSERT = "INSERT INTO transactions (date, amount, type, initiated_by, state, id_card) VALUES (?, ?, ?, ?, ?, ?)";

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
                transaction.setId(resultSet.getLong("id_transaction"));
                transaction.setDate(ZonedDateTime.of(resultSet.getTimestamp("date").toLocalDateTime(), ZoneId.systemDefault()));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setType(resultSet.getString("type"));
                transaction.setInitiated_by(resultSet.getString("initiated_by"));
                transaction.setState(resultSet.getString("state"));
                transaction.setId_card(resultSet.getLong("id_card"));
            }
            logger.info("findById method was invoked in TransactionDAO");
            return transaction.getId() != 0 ? transaction : null;
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }

    public List<Transaction> findByCardId(long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(FIND_BY_CARD_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while(resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getLong("id_transaction"));
                transaction.setDate(ZonedDateTime.of(resultSet.getTimestamp("date").toLocalDateTime(), ZoneId.systemDefault()));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setType(resultSet.getString("type"));
                transaction.setInitiated_by(resultSet.getString("initiated_by"));
                transaction.setState(resultSet.getString("state"));
                transaction.setId_card(resultSet.getLong("id_card"));
                transactionsList.add(transaction);
            }
            logger.info("findByCardId method was invoked in TransactionDAO");
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
                transaction.setId(resultSet.getLong("id_transaction"));
                transaction.setDate(ZonedDateTime.of(resultSet.getTimestamp("date").toLocalDateTime(), ZoneId.systemDefault()));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setType(resultSet.getString("type"));
                transaction.setInitiated_by(resultSet.getString("initiated_by"));
                transaction.setState(resultSet.getString("state"));
                transaction.setId_card(resultSet.getLong("id_card"));
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
            statement.setTimestamp(1, Timestamp.valueOf(transaction.getDate().toLocalDateTime()));
            statement.setBigDecimal(2, transaction.getAmount());
            statement.setString(3, transaction.getType());
            statement.setString(4, transaction.getInitiated_by());
            statement.setString(5, transaction.getState());
            statement.setLong(6, transaction.getId_card());
            statement.execute();
            logger.info("save method was invoked in TransactionDAO");
        } catch (SQLException ex) {
            logger.error("sql exception", ex);
            throw new RuntimeException(ex);
        }
    }
}
