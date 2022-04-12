package services;

import dao.cards.CardDAO;
import dao.transactions.TransactionDAO;
import dao.users.UserDAO;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import models.Transaction;
import models.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApplicationServiceTest {

    private static List<Transaction> histList;
    private static List<Transaction> histListId;
    private static List<User> usersList;
    private User user;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private CardDAO cardDAO;
    private ApplicationService applicationService;

    @BeforeClass
    public static void createTestLists() {
        //transactions' lists
        histList = new ArrayList<>();
        Transaction transaction1 = new Transaction(1, "Deposit", new BigDecimal(1000), ZonedDateTime.now());
        transaction1.setId(1);
        Transaction transaction2 = new Transaction(2, "Withdraw", new BigDecimal(2000), ZonedDateTime.now());
        transaction2.setId(2);
        Transaction transaction3 = new Transaction(1, "Deposit", new BigDecimal(500), ZonedDateTime.now());
        transaction3.setId(3);
        Transaction transaction4 = new Transaction(2, "Withdraw", new BigDecimal(100), ZonedDateTime.now());
        transaction4.setId(4);
        histList.add(transaction1);
        histList.add(transaction2);
        histList.add(transaction3);
        histList.add(transaction4);
        histListId = new ArrayList<>();
        for (Transaction tr : histList) {
            if (tr.getUserid() == 1) histListId.add(tr);
        }
        //users' list
        usersList = new ArrayList<>();
        User user1 = new User("Mike", "Mayers", new BigDecimal(1000));
        user1.setId(1);
        User user2 = new User("Spike", "Spayers", new BigDecimal(2000));
        user2.setId(2);
        User user3 = new User("John", "Smith", new BigDecimal(3000));
        user3.setId(3);
        usersList.add(user1);
        usersList.add(user2);
        usersList.add(user3);
    }

    @Before
    public void init() {
        transactionDAO = mock(TransactionDAO.class);
        userDAO = mock(UserDAO.class);
        cardDAO = mock(CardDAO.class);
        applicationService = new ApplicationService(userDAO, transactionDAO, cardDAO);
    }

    @Test
    public void getAllUsers() {
        when(userDAO.findAll()).thenReturn(usersList);
        ArrayList<User> listGot = (ArrayList<User>) applicationService.getAllUsers();
        Assert.assertTrue(listGot.size() > 0);
        Assert.assertEquals(listGot, usersList);
    }

    @Test
    public void getUserById() {
        User user = usersList.get(0);
        long id = usersList.get(0).getId();
        when(userDAO.findById(id)).thenReturn(user);
        User userGot = applicationService.getUserById(id);

        Assert.assertNotNull(userGot);
        Assert.assertEquals(userGot, user);
    }

    @Test
    public void getAllTransactions() {
        TransactionDAO transactionDAO = mock(TransactionDAO.class);
        UserDAO userDAO = mock(UserDAO.class);
        CardDAO cardDAO = mock(CardDAO.class);
        applicationService = new ApplicationService(userDAO, transactionDAO, cardDAO);
        when(transactionDAO.findAll()).thenReturn(histList);
        ArrayList<Transaction> listGot = (ArrayList<Transaction>) applicationService.getAllTransactions();
        Assert.assertTrue(listGot.size() > 0);
        Assert.assertEquals(listGot, histList);
    }

    @Test
    public void getTransactionsByUserId() {
        TransactionDAO transactionDAO = mock(TransactionDAO.class);
        UserDAO userDAO = mock(UserDAO.class);
        applicationService = new ApplicationService(userDAO, transactionDAO, cardDAO);
        long id = 1;
        when(transactionDAO.findByUserId(id)).thenReturn(histListId);
        ArrayList<Transaction> listGot = (ArrayList<Transaction>) applicationService.getTransactionsByUserId(id);
        Assert.assertTrue(listGot.size() > 0);
        Assert.assertEquals(listGot, histListId);
    }

    @Test
    public void getTransactionById() {
        long id = histList.get(0).getId();
        Transaction transaction = histList.get(0);
        when(transactionDAO.findById(id)).thenReturn(transaction);
        Transaction transactionGot = applicationService.getTransactionById(id);
        Assert.assertNotNull(transactionGot);
        Assert.assertEquals(transaction, transactionGot);
    }

    @Test
    public void depositMoneyTest() {
        doAnswer(invocation -> {
            long id = invocation.getArgument(0);
            BigDecimal amount = invocation.getArgument(1);

            Assert.assertEquals(1L, id);
            Assert.assertEquals(new BigDecimal(100), amount);
            for (User userNew: usersList) {
                if (userNew.getId() == id) {
                    userNew.setBalance(userNew.getBalance().add(amount));
                    user = userNew;
                    break;
                }
            }
            return user;
        }).when(userDAO).depositMoney(any(Long.class), any(BigDecimal.class));
        applicationService.depositMoney(1L, new BigDecimal(100));
        verify(userDAO, times(1)).depositMoney(1L, new BigDecimal(100));
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId(), 1);
    }

    @Test
    public void withdrawMoney() {
        long id = 1;
        BigDecimal amount = new BigDecimal(100);
        when(userDAO.withdrawMoney(id, amount)).thenReturn(1);
        applicationService.withdrawMoney(id, amount);
        verify(userDAO, times(1)).withdrawMoney(id, amount);
    }
}