package services;

import dao.cards.CardDAO;
import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import models.Card;
import models.Transaction;
import models.User;
import utils.DatabaseConnectionManager;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Log4j
@AllArgsConstructor
public class ApplicationService {
    private final static IllegalArgumentException ZERO_OR_NEGATIVE = new IllegalArgumentException("value is zero or negative");

    private final UserDAO userDAO;
    private final TransactionDAO transactionDAO;
    private final CardDAO cardDAO;

    public ApplicationService() {
        try {
            userDAO = new UserDAO(DatabaseConnectionManager.getConnection());
            transactionDAO = new TransactionDAO(DatabaseConnectionManager.getConnection());
            cardDAO = new CardDAO(DatabaseConnectionManager.getConnection());
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
                amount, ZonedDateTime.now());
        transactionDAO.save(transaction);
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

    public long getUserIdByAccountAndPin(BigDecimal account, BigDecimal pincode) {
        if (String.valueOf(account).length()<16 || String.valueOf(pincode).length()<4) {
            throw ZERO_OR_NEGATIVE;
        }
        return cardDAO.findUserId(account, pincode);
    }

    public Card getCardByAccountAndPin(BigDecimal account, BigDecimal pincode) {
        if (String.valueOf(account).length()<16 || String.valueOf(pincode).length()<4) {
            return null;
        }
        return cardDAO.findCardByAccAndPin(account, pincode);
    }

    public void destroy() {
        userDAO.closeConnection();
        transactionDAO.closeConnection();
        cardDAO.closeConnection();
    }
}
