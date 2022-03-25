package services;

import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import models.Transaction;
import models.User;
import org.apache.log4j.Logger;
import utils.BdCredentials;
import utils.DatabaseConnectionManager;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;

public class ApplicationService {
    private static final Logger logger = Logger.getLogger(ApplicationService.class);
    private final static IllegalArgumentException ZERO_OR_NEGATIVE = new IllegalArgumentException("value is zero or negative");

    private final UserDAO userDAO;
    private final TransactionDAO transactionDAO;

    public ApplicationService() {
        DatabaseConnectionManager databaseConnectionManager =
                new DatabaseConnectionManager(BdCredentials.getBdHost(), BdCredentials.getBdLogin(),
                        BdCredentials.getBdPassword(), BdCredentials.getBdName());
        try {
            userDAO = new UserDAO(databaseConnectionManager.getConnection());
        } catch (SQLException ex) {
            logger.error("userDAO was not connected to the database", ex);
            throw new RuntimeException(ex);
        }
        try {
            transactionDAO = new TransactionDAO(databaseConnectionManager.getConnection());
        } catch (SQLException ex) {
            logger.error("transactionDAO was not connected to the database", ex);
            throw new RuntimeException(ex);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = userDAO.findAll();
        return userList != null ? userList : List.of();
    }

    public User getUserById(long id) {
        if (id <= 0) {
            throw ZERO_OR_NEGATIVE;
        }
        return userDAO.findById(id);
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transList = transactionDAO.findAll();
        return transList != null ? transList : List.of();
    }

    public List <Transaction> getTransactionsByUserId (long id) {
        if (id <= 0) {
            throw ZERO_OR_NEGATIVE;
        }
        return transactionDAO.findByUserId(id);
    }

    public Transaction getTransactionById(long id) {
        if (id <= 0) {
            throw ZERO_OR_NEGATIVE;
        }
        return transactionDAO.findById(id);
    }

    public void depositMoney(long id, BigDecimal amount) {
        userDAO.depositMoney(id, amount);
        Transaction transaction = new Transaction(id, "Deposit",
                amount, ZonedDateTime.now());
        transactionDAO.save(transaction);
    }

    public void withdrawMoney(long id, BigDecimal amount){
        userDAO.withdrawMoney(id, amount);
        Transaction transaction = new Transaction(id, "Withdraw",
                amount, new Timestamp(System.currentTimeMillis()));
        transactionDAO.save(transaction);
    }

    public void destroy() {
        userDAO.closeConnection();
        transactionDAO.closeConnection();
    }
}
