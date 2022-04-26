package services;

import dao.cards.CardDAO;
import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import models.Card;
import models.CardStatus;
import models.Transaction;
import models.User;
import utils.DatabaseConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Log4j
@AllArgsConstructor
public class ApplicationService {
    private final static IllegalArgumentException ZERO_OR_NEGATIVE = new IllegalArgumentException("value is zero or negative");
    private final UserDAO userDAO;
    private final TransactionDAO transactionDAO;
    private final CardDAO cardDAO;
    private Connection connection;

    @SneakyThrows
    public ApplicationService() {
        try {
            connection = DatabaseConnectionManager.getConnection();
            connection.setAutoCommit(false);
            userDAO = new UserDAO(connection);
            transactionDAO = new TransactionDAO(connection);
            cardDAO = new CardDAO(connection);
        } catch (RuntimeException ex) {
            logger.error("dao initialization error", ex);
            throw new RuntimeException(ex);
        }
    }

    public ApplicationService(UserDAO userDAO, TransactionDAO transactionDAO) {
        this.userDAO = userDAO;
        this.transactionDAO = transactionDAO;
        try {
            this.cardDAO = new CardDAO(DatabaseConnectionManager.getConnection());
        } catch (RuntimeException ex) {
            logger.error("dao initialization error", ex);
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

    public List <Transaction> getTransactionsByCardId(long id) {
        if (id <= 0) {
            throw ZERO_OR_NEGATIVE;
        }
        return transactionDAO.findByCardId(id);
    }

    public List <Transaction> getTransactionsByUserId(long id) {
        if (id <= 0) {
            throw ZERO_OR_NEGATIVE;
        }
        List <Card> cardsList = cardDAO.findByUserId(id);
        List <Transaction> transactionList = new ArrayList<>();
        for (Card card : cardsList) {
            transactionList.addAll(transactionDAO.findByCardId(card.getId()));
        }

        Collections.sort(transactionList, Comparator.comparing(Transaction::getDate));

        return transactionList;
    }

    public Transaction getTransactionById(long id) {
        if (id <= 0) {
            throw ZERO_OR_NEGATIVE;
        }
        return transactionDAO.findById(id);
    }

    @SneakyThrows
    public void depositMoney(long id, BigDecimal amount, String initiator) {
        try {
            cardDAO.depositMoney(id, amount);
            Transaction transaction = new Transaction(ZonedDateTime.now(), amount, "Deposit", initiator,
                    "Done", id);
            transactionDAO.save(transaction);
            connection.commit();
            logger.info("Deposit money operation was done successfully");
        } catch (SQLException | RuntimeException e) {
            logger.error("SQL exception occurred", e);
            connection.rollback();
            Transaction transaction = new Transaction(ZonedDateTime.now(), amount, "Deposit", initiator,
                    "Error", id);
            transactionDAO.save(transaction);
            connection.commit();
        }
    }
    @SneakyThrows
    public void withdrawMoney(long id, BigDecimal amount, String initiator) {
        try {
            cardDAO.withdrawMoney(id, amount);
            Transaction transaction = new Transaction(ZonedDateTime.now(), amount, "Withdraw", initiator, "Done", id);
            transactionDAO.save(transaction);
            connection.commit();
            logger.info("Withdraw money operation was done successfully");
        } catch (SQLException | RuntimeException e) {
            logger.error("SQL exception occurred", e);
            connection.rollback();
            Transaction transaction = new Transaction(ZonedDateTime.now(), amount, "Withdraw", initiator, "Error", id);
            transactionDAO.save(transaction);
            connection.commit();
        }
    }
    @SneakyThrows
    public void transferMoney(long idFrom, long idTo, BigDecimal amount, String initiator) {
        try {
            cardDAO.withdrawMoney(idFrom, amount);
            Transaction transaction = new Transaction(ZonedDateTime.now(), amount, "Withdraw", initiator, "Done", idFrom);
            transactionDAO.save(transaction);
            logger.info("Withdraw money operation in transfer method was done successfully");

            cardDAO.depositMoney(idTo, amount);
            transaction = new Transaction(ZonedDateTime.now(), amount, "Deposit", initiator, "Done", idTo);
            transactionDAO.save(transaction);
            logger.info("Deposit money operation in transfer method was done successfully");
            connection.commit();
            logger.info("Transfer money operation was done successfully");
        } catch (SQLException | RuntimeException e) {
            logger.error("SQL exception occurred", e);
            connection.rollback();
            Transaction transaction = new Transaction(ZonedDateTime.now(), amount, "Withdraw", initiator, "Error", idFrom);
            transactionDAO.save(transaction);
            connection.commit();
        }
    }

    public List<Card> getAllCards() {
        List<Card> cardsList = cardDAO.findAll();
        return cardsList != null ? cardsList : List.of();
    }

    public List<Card> getAllCardsByUserId(long id) {
        if (id <= 0) {
            throw ZERO_OR_NEGATIVE;
        }
        List<Card> cardsList = cardDAO.findByUserId(id);
        return cardsList != null ? cardsList : List.of();
    }

    public Card getCardById(long id) {
        if (id <= 0) {
            throw ZERO_OR_NEGATIVE;
        }
        return cardDAO.findById(id);
    }

    public long getUserIdByAccountAndPin(BigDecimal account, String pincode) {
        if (String.valueOf(account).length()<16 || String.valueOf(pincode).length()<4) {
            throw ZERO_OR_NEGATIVE;
        }
        return cardDAO.findUserId(account, pincode);
    }

    public Card getCardByAccountAndPin(BigDecimal account, String pincode) {
        if (String.valueOf(account).length()<16 || String.valueOf(pincode).length()<4) {
            return null;
        }
        return cardDAO.findCardByAccAndPin(account, pincode);
    }

    public Card getCardByAccount(BigDecimal account) {
        if (String.valueOf(account).length()<16) {
            return null;
        }
        return cardDAO.findByAccount(account);
    }

    @SneakyThrows
    public void closeCard(long account) {
        cardDAO.changeCardStatus(account, CardStatus.CLOSED);
        connection.commit();
    }

    public void updateCardPin(BigDecimal account, String pincode) {
        cardDAO.updatePin(account, pincode);
    }

    public void destroy() {
        userDAO.closeConnection();
        transactionDAO.closeConnection();
        cardDAO.closeConnection();
    }
}
